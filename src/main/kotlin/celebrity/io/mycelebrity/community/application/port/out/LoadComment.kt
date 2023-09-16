package celebrity.io.mycelebrity.community.application.port.out

import celebrity.io.mycelebrity.community.domain.Comment

interface LoadComment {

    fun loadComment(
        comment: Comment,
        communityId: Long,
    ): Unit
}
