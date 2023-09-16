package celebrity.io.mycelebrity.rank.adapter.out.persistence.repository

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import celebrity.io.mycelebrity.rank.adapter.out.persistence.entity.RankHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RankHistoryJpaRepository : JpaRepository<RankHistoryEntity, Long>{

    fun findByCelebrity(celebrityEntity: CelebrityEntity): RankHistoryEntity?
}
