package celebrity.io.mycelebrity.rank.application.dto

data class RankResponse(
    val celebrityId: Long,
    val rank: Long,
    val rankMovement: Long,
    val viewCount: Long,
    val celebrityDescription: String,
    val celebrityName: String,
)
