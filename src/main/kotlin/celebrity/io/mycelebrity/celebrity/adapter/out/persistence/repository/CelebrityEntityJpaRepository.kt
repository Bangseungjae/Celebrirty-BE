package celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CelebrityEntityJpaRepository : JpaRepository<CelebrityEntity, Long> {
}
