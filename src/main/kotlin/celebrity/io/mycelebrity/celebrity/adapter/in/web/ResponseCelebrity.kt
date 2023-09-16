package celebrity.io.mycelebrity.celebrity.adapter.`in`.web

import celebrity.io.mycelebrity.celebrity.domain.Celebrity

data class ResponseCelebrity(
    val id: Long,
    val name: String,
    val group: String,
    val description: String,
    val url: String,
)

fun Celebrity.toResponseCelebrity() =
    ResponseCelebrity(
        id = id,
        name = name,
        group = group,
        description = description,
        url = "null",
    )
