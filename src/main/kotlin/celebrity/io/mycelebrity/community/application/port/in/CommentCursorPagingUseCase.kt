package celebrity.io.mycelebrity.community.application.port.`in`

import celebrity.io.mycelebrity.common.dto.Cursor
import celebrity.io.mycelebrity.common.dto.CursorResult
import celebrity.io.mycelebrity.community.application.dto.CommentResponse

interface CommentCursorPagingUseCase {

    fun findByCursor(cursor: Cursor, communityId: Long): CursorResult<CommentResponse>
}
