package celebrity.io.mycelebrity.celebrity.adapter.out.persistence

import celebrity.io.mycelebrity.celebrity.adapter.`in`.web.ResponseCelebrity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.QCelebrityEntity.*
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityEntityJpaRepository
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityGroupEntityJpaRepository
import celebrity.io.mycelebrity.celebrity.application.dto.RequestCelebrity
import celebrity.io.mycelebrity.celebrity.application.port.out.CreateCelebrityPort
import celebrity.io.mycelebrity.celebrity.application.port.out.FindCelebrityPort
import celebrity.io.mycelebrity.celebrity.domain.Celebrity
import celebrity.io.mycelebrity.common.annotation.PersistenceAdapter
import celebrity.io.mycelebrity.common.persistence.entity.Deleted
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.QCommunityEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.QCommunityEntity.communityEntity
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import java.lang.IllegalArgumentException

@PersistenceAdapter
class CelebrityPersistenceAdapter(
    private val queryFactory: JPAQueryFactory,
    private val celebrityEntityJpaRepository: CelebrityEntityJpaRepository,
    private val celebrityGroupEntityJpaRepository: CelebrityGroupEntityJpaRepository,
) : FindCelebrityPort, CreateCelebrityPort {

    override fun findAllCelebrity(): List<ResponseCelebrity> {
        return queryFactory.select(
            Projections.constructor(
                ResponseCelebrity::class.java,
                celebrityEntity.id,
                celebrityEntity.name,
                celebrityEntity.group.name,
                celebrityEntity.description,
                communityEntity.url,
                )
        )
            .from(celebrityEntity, communityEntity)
            .where(
                celebrityEntity.deleted.eq(Deleted.NOT)
                    .and(communityEntity.eq(celebrityEntity.community))
            )
            .fetch()
    }

    override fun createCelebrity(request: RequestCelebrity, groupId: Long): Long {
        val groupEntity = celebrityGroupEntityJpaRepository.findById(groupId)
            .orElseThrow { IllegalArgumentException("해당 id의 그룹이 없습니다.") }
        val celebrityEntity = CelebrityEntity(
            name = request.celebrityName,
            description = request.celebrityDescription,
            group = groupEntity,
        )
        return celebrityEntityJpaRepository.save(celebrityEntity).id!!
    }
}
