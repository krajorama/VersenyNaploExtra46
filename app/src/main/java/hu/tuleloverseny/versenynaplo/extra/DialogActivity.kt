package hu.tuleloverseny.versenynaplo.extra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_fullscreen.*
import java.util.ArrayList
import java.util.regex.Pattern

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class DialogActivity : AppCompatActivity() {
    // DON'T TOUCH
    // Extra hívással az Versenynapló-ból átadott értékek
    var extraVersionName: String? = null
    var extraProduction: Boolean = false
    var extraActivated: Boolean = false
    var extraNightMode: Boolean = false
    var extraTeamCategory: String? = null
    var extraVersenyzo: String? = null
    var extraTurazoSorszam: Long = 0
    var extraNaploRowNum: Long = 0
    var extraNaploList: MutableList<Array<String?>> = object : ArrayList<Array<String?>>() {}
    //        "Feltoltve", "Ervenytelenitve", "Visszaigazolva",
    //        "BejarasiSorszam", "ErkezesDayTime", "ErkezesDateTime", "PointName", "KeyCode",
    //        "OtthagyottStafetusz", "MegtalaltStafetusz",
    //        "Bonusz", "ExtraInfo1", "ExtraInfo2", "ExtraInfo3", "Megjegyzes"

    // DON'T TOUCH
    // Extra befejezésekor átadandó, kiszámolt értékek
    var extraExtraInfo1: String? = null
    var extraExtraInfo2: String? = null
    var extraExtraInfo3: String? = null

    // DON'T TOUCH
    // Extra meghívásakor lefutó inicializáció
    private fun extraInit(): Boolean {

        val intent = intent

        extraVersionName = intent.getStringExtra("VersionName")
        extraProduction = intent.getBooleanExtra("Production", false)
        extraActivated = intent.getBooleanExtra("Activated", false)
        extraNightMode = intent.getBooleanExtra("NightMode", false)
        extraTeamCategory = intent.getStringExtra("TeamCategory")
        extraVersenyzo = intent.getStringExtra("Versenyzo")
        extraTurazoSorszam = intent.getLongExtra("TurazoSorszam", 0)

        extraNaploRowNum = intent.getIntExtra("NaploRowNum", 0).toLong()
        for (i in 0 until extraNaploRowNum) {
            val naploRow: Array<String?>? = intent.getStringArrayExtra("NaploRow$i")
            if (naploRow != null) extraNaploList.add(naploRow)
        }

        if (extraVersionName == null) {
            Toast.makeText(
                applicationContext,
                resources.getString(R.string.missing_intent) + "\n" + resources.getString(R.string.versionName),
                Toast.LENGTH_LONG
            ).show()
            setResult(RESULT_CANCELED)
            finish()
            return false
        }

        if (!extraActivated) {
            Toast.makeText(
                applicationContext,
                "Még nincs aktiválva az alkalmazás!",
                Toast.LENGTH_LONG
            ).show()
            extraExtraInfo1="I"
            extraExtraInfo2="I"
            extraExtraInfo3="I"
            extraFinish()
            return false
        }

        if (extraNaploRowNum.toInt() != extraNaploList.size) {
            Toast.makeText(
                applicationContext,
                "Nem jött át minden napló sor!",
                Toast.LENGTH_LONG
            ).show()
            setResult(RESULT_CANCELED)
            finish()
            return false
        }

        Log.d(
            "DialogActivity",
            "onCreate split " + extraVersionName!!.split(Pattern.quote("-").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        )

        if (extraVersionName!!.split("-")[0] !=
            resources.getString(R.string.versionName).split("-")[0]) {
            Toast.makeText(applicationContext, R.string.version_missmatch, Toast.LENGTH_LONG).show()
            setResult(RESULT_CANCELED)
            finish()
            return false
        }

        if (extraTeamCategory == null || extraVersenyzo == null || extraTurazoSorszam == 0L) {
            Toast.makeText(applicationContext, R.string.missing_params, Toast.LENGTH_LONG).show()
            setResult(RESULT_CANCELED)
            finish()
            return false
        }

        val pointName: String? = extraNaploList[extraNaploRowNum.toInt()-1][PointNameIdx]
        if (pointName == null) {
            Toast.makeText(
                applicationContext,
                if (pointName == null)
                    "A tereppont azonosítója nincs megadva."
                else
                    "A tereppont azonosítója ismeretlen.",
                Toast.LENGTH_LONG
            ).show()
            setResult(RESULT_CANCELED)
            finish()
            return false
        }

        // éjszakai app-hoz éjszakai Extra
        if (extraNightMode) {
            setTheme(R.style.AppTheme_Night)
        }

        return true
    }


    // DON'T TOUCH
    // Extra befejezésekor kell lefutnia, ez adja vissza az Extra által számoltakat
    private fun extraFinish() {

        // a konkrét érték átadás
        val backIntent = Intent()
        backIntent.putExtra("VersionName", resources.getString(R.string.versionName))
        backIntent.putExtra("Versenyzo", extraVersenyzo)
        backIntent.putExtra("TurazoSorszam", extraTurazoSorszam)
        backIntent.putExtra("ExtraInfo1", extraExtraInfo1)
        backIntent.putExtra("ExtraInfo2", extraExtraInfo2)
        backIntent.putExtra("ExtraInfo3", extraExtraInfo3)

        setResult(RESULT_OK, backIntent)
        finish()

    }

    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        fullscreen_content.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Config.load(applicationContext.getExternalFilesDir(null))

        if (!extraInit()) {
            return
        }

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mVisible = true

        // Set up the user interaction to manually show or hide the system UI.
        fullscreen_content.setOnClickListener { toggle() }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        clear_button.setOnTouchListener(mDelayHideTouchListener)
        undo_button.setOnClickListener { gameView.doUndo()  }
        done_button.setOnClickListener {
            extraExtraInfo1 = gameView.getExtraInfo1()
            extraExtraInfo2 = gameView.getExtraInfo2()
            extraExtraInfo3 = gameView.getExtraInfo3()
            extraFinish()
        }

        clear_button.setOnClickListener {
            extraFinish()
        }

        val gameState = GameState()
        gameView.gameState = gameState
        gameView.doneButton = done_button
        gameView.disableDoneButton()

        if (!placeOldShapes(gameState)) {
            Toast.makeText(applicationContext, "Betelt a pálya, nem lehet több elemet lerakni", Toast.LENGTH_LONG).show()
            val playerMove = PlayerMove.gameIsFull()
            extraExtraInfo1 = playerMove.info1
            extraExtraInfo2 = playerMove.info2
            extraExtraInfo3 = playerMove.info3
            extraFinish()
        }

        if (!gameState.addNextShape(
                ShapeDir.getShape(extraTeamCategory!!, extraNaploList[extraNaploList.size-1][PointNameIdx] ?: ""))) {
                gameView.enableDoneButton()
        }
    }

    private fun placeOldShapes(gameState: GameState): Boolean {
        if (extraNaploList.size < 2) // no old records
            return true

        val oldEntriesInNaplo = extraNaploList.subList(0, extraNaploList.size - 1)

        for (naploRow in oldEntriesInNaplo) {
            val extraInfo1: String? = naploRow[ExtraInfo1Idx]
            val extraInfo2: String? = naploRow[ExtraInfo2Idx]
            val extraInfo3: String? = naploRow[ExtraInfo3Idx]

            if (extraInfo1 == null || extraInfo2 == null || extraInfo3 == null)
                continue

            val playerMove = PlayerMove(extraInfo1, extraInfo2, extraInfo3)

            if (playerMove.isFull()) return false

            if (!playerMove.isValidPlacement())
                continue

            gameState.addFinalShape(shape = playerMove.getShape())
        }

        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        fullscreen_content.visibility = View.GONE
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300

        val InvalidatedIdx = 1
        val PointNameIdx = 6
        val ExtraInfo1Idx = 11
        val ExtraInfo2Idx = 12
        val ExtraInfo3Idx = 13
    }
}
