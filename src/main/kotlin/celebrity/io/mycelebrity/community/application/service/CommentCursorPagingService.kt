package celebrity.io.mycelebrity.community.application.service

import celebrity.io.mycelebrity.common.annotation.UseCase
import celebrity.io.mycelebrity.common.dto.Cursor
import celebrity.io.mycelebrity.common.dto.CursorResult
import celebrity.io.mycelebrity.community.application.dto.CommentResponse
import celebrity.io.mycelebrity.community.application.port.`in`.CommentCursorPagingUseCase
import celebrity.io.mycelebrity.community.application.port.out.CommentPagingPort
import org.springframework.transaction.annotation.Transactional

@UseCase
class CommentCursorPagingService(
    private val commentPagingPort: CommentPagingPort,
) : CommentCursorPagingUseCase {

    @Transactional(readOnly = true)
    override fun findByCursor(cursor: Cursor, communityId: Long): CursorResult<CommentResponse> {
        val commentResponses: List<CommentResponse> = cursor.next?.run {
            commentPagingPort.cursorPaging(
                next = this,
                size = cursor.size,
                communityId = communityId,
            )
        } ?: run {
            commentPagingPort.cursorPaging(
                size = cursor.size,
                communityId = communityId
            )
        }

        val lastIndex = if (commentResponses.isEmpty()) 0L else commentResponses[commentResponses.lastIndex].id
        val hasNext: Boolean = commentPagingPort.hasNext(
            communityId = communityId,
            lastIndex = lastIndex,
        )
        return CursorResult(
            values = commentResponses,
            hasNext = hasNext,
            lastIndex = lastIndex,
        )
    }
}
