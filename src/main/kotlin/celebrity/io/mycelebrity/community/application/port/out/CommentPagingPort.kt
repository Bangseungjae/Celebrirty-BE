package celebrity.io.mycelebrity.community.application.port.out

import celebrity.io.mycelebrity.community.application.dto.CommentResponse

interface CommentPagingPort {

    fun cursorPaging(next: Long, size: Long, communityId: Long): List<CommentResponse>
    fun cursorPaging(size: Long, communityId: Long): List<CommentResponse>
    fun hasNext(communityId: Long, lastIndex: Long): Boolean
}
