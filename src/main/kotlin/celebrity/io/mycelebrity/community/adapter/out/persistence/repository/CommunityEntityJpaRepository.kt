package celebrity.io.mycelebrity.community.adapter.out.persistence.repository

import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.CommunityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommunityEntityJpaRepository: JpaRepository<CommunityEntity, Long> {
}
