package hu.tuleloverseny.versenynaplo.extra

import java.lang.RuntimeException


class TeamResults(
    val teamId: String,
    val level: String,
    val playingField: PlayingField,
    val score: Int = 0,
    val noScoreUUIDs: List<String> = emptyList(),
    val entryIds: List<String> = emptyList()
)

class BadEntry(
    val teamId: String,
    val entryId: String
)

class EvalResults {
    var results: Map<String, TeamResults> = emptyMap()

    var foundBadEntries: Int = 0
    val badEntries: List<BadEntry> = listOf(
        BadEntry("TE46-45", "01"),
        BadEntry("TE46-3", "06"),
        BadEntry("TE46-9", "06"),
        BadEntry("TE46-10", "06"),
        BadEntry("TE46-14", "11"),
        BadEntry("TE46-34", "11"),
        BadEntry("TE46-49", "11"),
        BadEntry("TE46-47", "12"),
        BadEntry("TE46-30", "15"),
        BadEntry("TE46-32", "18"),
        BadEntry("TE46-36", "18"),
        BadEntry("TE46-52", "18"),
        BadEntry("TE46-15", "21"),
        BadEntry("TE46-21", "21"),
        BadEntry("TE46-24", "31"),
        BadEntry("TE46-1", "34"),
        BadEntry("TE46-2", "34"),
        BadEntry("TE46-3", "34"),
        BadEntry("TE46-10", "34"),
        BadEntry("TE46-30", "34"),
        BadEntry("TE46-40", "34"),
        BadEntry("TE46-51", "42"),
        BadEntry("TE46-31", "43"),
        BadEntry("TE46-15", "45"),
        BadEntry("TE46-52", "47"),
        BadEntry("TE46-35", "51"),
        BadEntry("TE46-10", "54"),
        BadEntry("TE46-41", "55"),
        BadEntry("TE46-3", "56"),
        BadEntry("TE46-30", "56"),
        BadEntry("TE46-45", "57"),
        BadEntry("TE46-53", "61"),
        BadEntry("TE46-57", "61"),
        BadEntry("TE46-51", "66"),
        BadEntry("TE46-47", "68"),
        BadEntry("TE46-46", "69"),
        BadEntry("TE46-52", "70"),
        BadEntry("TE46-35", "75"),
        BadEntry("TE46-13", "79"),
        BadEntry("TE46-34", "79"),
        BadEntry("TE46-38", "79"),
        BadEntry("TE46-41", "79"),
        BadEntry("TE46-47", "79"),
        BadEntry("TE46-2", "87"),
        BadEntry("TE46-29", "87"),
        BadEntry("TE46-34", "87"),
        BadEntry("TE46-37", "87"),
        BadEntry("TE46-47", "87"),
        BadEntry("TE46-14", "89"),
        BadEntry("TE46-17", "89"),
        BadEntry("TE46-32", "89"),
        BadEntry("TE46-35", "89"),
        BadEntry("TE46-41", "89")
    )

    fun evalEntry(
        teamId: String,
        level: String,
        timeOfEntry: String,
        entryId: String,
        info1: String,
        info2: String,
        info3: String,
        isValid: Boolean = true
    ) {
        val prevTeamResults: TeamResults = results.getOrElse(
            teamId) { TeamResults(teamId, level, PlayingField(Config.rowCount, Config.columnCount)) }

        val newShape = ShapeDir.getShape(level, entryId)
        val playerMove = PlayerMove(info1, info2, info3)

        if (!playerMove.isValidPlacement()) {// put down the shape
            throw RuntimeException("Not a valid move")
        }

        val newPlacedShapeNoUUID = playerMove.getShape()

        if (newPlacedShapeNoUUID.shape.label != newShape.label) {  // no mixup with point name vs shape
            throw RuntimeException("Wrong shape in tetris for this entryId")
        }
        val uuid = "$teamId;$timeOfEntry;$entryId"
        val newPlacedShape = newPlacedShapeNoUUID.addUUID(uuid)

        val newPlayingField = prevTeamResults.playingField.cloneAndMove(newPlacedShape.activate())
        if (newPlayingField == prevTeamResults.playingField) {// there was real change
            throw RuntimeException("No real change!")
        }
        if (newPlayingField.isActiveShapeConflict()) {
            throw RuntimeException("Conflict!")
        }

        if (isValid && prevTeamResults.entryIds.contains(entryId)) {
            throw RuntimeException("Dupla felvetel! $teamId $entryId")
        }

        val isBadEntry = badEntries.find{ it.teamId == teamId && it.entryId==entryId} != null
        val addToNoScore: Boolean = !isValid || isBadEntry
        if (isBadEntry) {
            foundBadEntries++
        }

        val noScoreUUIDs = if (!addToNoScore) prevTeamResults.noScoreUUIDs else prevTeamResults.noScoreUUIDs.plus(uuid)

        val scored: Pair<PlayingField, Int> = newPlayingField.score(noScoreUUIDs)
        val teamResults = TeamResults(
            teamId,
            level,
            scored.first,
            prevTeamResults.score + scored.second,
            noScoreUUIDs,
            if (isValid) prevTeamResults.entryIds.plus(entryId) else prevTeamResults.entryIds
        )

        results = (results.filterNot { it.key == teamId }) + (teamId to teamResults)
    }
}
