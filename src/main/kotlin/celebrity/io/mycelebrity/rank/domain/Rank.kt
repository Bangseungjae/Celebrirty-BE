package celebrity.io.mycelebrity.rank.domain

data class Rank(
    val celebrityId: Long,
    val celebrityDescription: String,
    val viewCount: Long,
    val celebrityName: String,
) {
    var rank: Long = 0
}

