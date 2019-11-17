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

class EvalResults {
    var results: Map<String, TeamResults> = emptyMap()

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

        val noScoreUUIDs = if (isValid) prevTeamResults.noScoreUUIDs else prevTeamResults.noScoreUUIDs.plus(uuid)

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
