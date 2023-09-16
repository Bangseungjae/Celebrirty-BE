package celebrity.io.mycelebrity.common.domain

data class Id(
    val id: Long,
) {
    init {
        require(id > 0L) {
            IllegalArgumentException("id의 값은 1 이상이여야 합니다.")
        }
    }
}
