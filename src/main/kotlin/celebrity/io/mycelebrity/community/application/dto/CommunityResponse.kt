package celebrity.io.mycelebrity.community.application.dto

data class CommunityResponse(
    val communityId: Long,
    val description: String,
    val celebrityName: String,
    val url: String,
) {
}
