package celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityGroupEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CelebrityGroupEntityJpaRepository : JpaRepository<CelebrityGroupEntity, Long> {
}
