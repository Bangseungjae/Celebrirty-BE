package celebrity.io.mycelebrity.community.adapter.out.persistence.repository

import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentEntityJpaRepository : JpaRepository<CommentEntity, Long>{
}
