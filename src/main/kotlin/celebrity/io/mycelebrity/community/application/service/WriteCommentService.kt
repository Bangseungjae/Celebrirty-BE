package celebrity.io.mycelebrity.community.application.service

import celebrity.io.mycelebrity.common.annotation.UseCase
import celebrity.io.mycelebrity.common.domain.Id
import celebrity.io.mycelebrity.community.application.dto.CommentRequest
import celebrity.io.mycelebrity.community.application.dto.toComment
import celebrity.io.mycelebrity.community.application.port.`in`.LoadCommentUseCase
import celebrity.io.mycelebrity.community.application.port.out.LoadComment
import org.springframework.transaction.annotation.Transactional

@UseCase
class WriteCommentService(
    private val loadComment: LoadComment,
) : LoadCommentUseCase {
    @Transactional
    override fun writeComment(communityId: Id, commentRequest: CommentRequest) {
        loadComment.loadComment(
            communityId = communityId.id,
            comment = commentRequest.toComment()
        )
    }
}
