package celebrity.io.mycelebrity.common.helper.celebrity

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityGroupEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityEntityJpaRepository
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityGroupEntityJpaRepository
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.CommunityEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.repository.CommunityEntityJpaRepository
import org.springframework.stereotype.Component

@Component
class CelebrityUtils(
    private val celebrityEntityJpaRepository: CelebrityEntityJpaRepository,
    private val celebrityGroupEntityJpaRepository: CelebrityGroupEntityJpaRepository,
    private val communityEntityJpaRepository: CommunityEntityJpaRepository,
) {

    fun generateCelebrity(): Long {
        val celebrityGroupEntity = CelebrityGroupEntity(
            name = "newJeans",
        )
        val save = celebrityGroupEntityJpaRepository.save(celebrityGroupEntity)
        val celebrityEntity = CelebrityEntity(
            name = "HANI",
            description = "cute",
            group = save,
        )
        val save1 = celebrityEntityJpaRepository.save(celebrityEntity)
        val communityEntity = CommunityEntity(
            celebrity = celebrityEntity,
            description = "커뮤니티 설명",
            url = "www.ac.kr",
        )
        communityEntityJpaRepository.save(communityEntity)
        return save1.id!!
    }

    fun insertOnlyCelebrity(): Long {
        val celebrityGroupEntity = CelebrityGroupEntity(
            name = "newJeans",
        )
        val save = celebrityGroupEntityJpaRepository.save(celebrityGroupEntity)
        val celebrityEntity = CelebrityEntity(
            name = "HANI",
            description = "cute",
            group = save,
        )
        val save1 = celebrityEntityJpaRepository.save(celebrityEntity)
        return save1.id!!
    }
}
