package celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CelebrityHistoryJpaRepository : JpaRepository<CelebrityHistoryEntity, Long> {

}
