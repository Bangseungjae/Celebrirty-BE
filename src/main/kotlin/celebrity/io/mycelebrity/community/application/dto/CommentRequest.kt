package celebrity.io.mycelebrity.community.application.dto

import celebrity.io.mycelebrity.community.domain.Comment
import jakarta.validation.constraints.NotEmpty

data class CommentRequest(
    @NotEmpty
    val comment: String,
)

fun CommentRequest.toComment(): Comment =
    Comment(
        comment = comment,
    )
