package celebrity.io.mycelebrity.common.helper.community

import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.CommentEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.repository.CommentEntityJpaRepository
import celebrity.io.mycelebrity.community.adapter.out.persistence.repository.CommunityEntityJpaRepository
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class CommentUtils(
    private val communityEntityJpaRepository: CommunityEntityJpaRepository,
    private val commentEntityJpaRepository: CommentEntityJpaRepository,
) {

    fun createComment(communityId: Long) {
        val communityEntity = communityEntityJpaRepository.findById(communityId)
            .orElseThrow { IllegalArgumentException("해당 id의 커뮤니티는 찾을 수 없습니다.") }

        val commentList: MutableList<CommentEntity> = mutableListOf()

        for (i in 1..10) {
            commentList.add(
                CommentEntity(
                    community = communityEntity,
                    content = "$i hello"
                )
            )
        }
        commentEntityJpaRepository.saveAll(commentList)
    }
}
