package celebrity.io.mycelebrity.celebrity.domain

import celebrity.io.mycelebrity.celebrity.application.dto.RequestUpCount

data class ViewCount(
    val from: String,
    val to: Long,
) {
    init {
        require(to > 0) {
            "to는 1 이상이여야 합니다."
        }
    }
}

fun RequestUpCount.toViewCount() =
    ViewCount(
        from = from,
        to = to,
    )
