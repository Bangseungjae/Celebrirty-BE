package celebrity.io.mycelebrity.community.domain

data class Community(
    val id: Long? = null,
    val description: String,
    val celebrityId: Long,
    val url: String,
) {
    init {
        require(description.length < 100) {
            "커뮤니티 설명은 100글자를 넘으면 안됩니다."
        }
        require(url.length in 6..999) {
            "허용 URL 길이는 6~999 사이입니다"
        }

    }
}
