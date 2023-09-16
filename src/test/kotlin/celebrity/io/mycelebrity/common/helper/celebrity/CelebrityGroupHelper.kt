package celebrity.io.mycelebrity.common.helper.celebrity

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityGroupEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityGroupEntityJpaRepository
import org.springframework.stereotype.Component

@Component
class CelebrityGroupHelper(
    private val celebrityGroupEntityJpaRepository: CelebrityGroupEntityJpaRepository,
) {

    fun createGroup(): Long = CelebrityGroupEntity(
        name = "newJeans",
    ).let { celebrityGroupEntityJpaRepository.save(it).id!! }
}
