package celebrity.io.mycelebrity.common.helper.community

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityEntityJpaRepository
import celebrity.io.mycelebrity.common.helper.celebrity.CelebrityUtils
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.CommunityEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.repository.CommunityEntityJpaRepository
import org.springframework.stereotype.Component

@Component
class CommunityUtils(
    private val communityEntityJpaRepository: CommunityEntityJpaRepository,
    private val celebrityUtils: CelebrityUtils,
    private val celebrityEntityJpaRepository: CelebrityEntityJpaRepository,
) {

    fun generateCommunity(): Long {
        val celebrityId: Long = celebrityUtils.insertOnlyCelebrity()
        val celebrityEntity = celebrityEntityJpaRepository.findById(celebrityId)
            .get()
        val communityEntity = CommunityEntity(
            celebrity = celebrityEntity,
            description = "멋진 아이돌 커뮤니티",
            url = "aws.ac.kr"
        )
        val savedId: CommunityEntity = communityEntityJpaRepository.save(communityEntity)
        return savedId.id!!
    }
}
