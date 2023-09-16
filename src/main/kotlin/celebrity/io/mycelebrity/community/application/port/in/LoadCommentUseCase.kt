package celebrity.io.mycelebrity.community.application.port.`in`

import celebrity.io.mycelebrity.common.domain.Id
import celebrity.io.mycelebrity.community.application.dto.CommentRequest

interface LoadCommentUseCase {

    fun writeComment(
        communityId: Id,
        commentRequest: CommentRequest,
    )
}
