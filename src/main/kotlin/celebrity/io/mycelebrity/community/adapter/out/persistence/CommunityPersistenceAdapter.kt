package celebrity.io.mycelebrity.community.adapter.out.persistence

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.QCelebrityEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.QCelebrityEntity.celebrityEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityEntityJpaRepository
import celebrity.io.mycelebrity.common.annotation.PersistenceAdapter
import celebrity.io.mycelebrity.common.persistence.entity.Deleted
import celebrity.io.mycelebrity.community.application.dto.CommunityResponse
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.CommunityEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.QCommunityEntity.communityEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.repository.CommunityEntityJpaRepository
import celebrity.io.mycelebrity.community.application.port.out.LoadCommunity
import celebrity.io.mycelebrity.community.application.port.out.ViewCommunity
import celebrity.io.mycelebrity.community.domain.Community
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import java.lang.IllegalArgumentException

@PersistenceAdapter
class CommunityPersistenceAdapter(
    private val communityEntityJpaRepository: CommunityEntityJpaRepository,
    private val query: JPAQueryFactory,
) : LoadCommunity, ViewCommunity {

    override fun loadCommunity(community: Community) {
        val celebrityEntity: CelebrityEntity = (query.select(celebrityEntity)
            .from(celebrityEntity)
            .where(
                celebrityEntity.id.eq(community.celebrityId)
                    .and(celebrityEntity.deleted.eq(Deleted.NOT))
            )
            .fetchOne()
            ?: throw IllegalArgumentException("해당 id의 셀럽은 존재하지않습니다."))

        val communityEntity = CommunityEntity(
            celebrity = celebrityEntity,
            description = community.description,
            url = community.url,
        )
        communityEntityJpaRepository.save(communityEntity)
    }

    override fun getAllCommunity(): List<CommunityResponse> =
        query.select(
            Projections.constructor(
                CommunityResponse::class.java,
                communityEntity.id,
                communityEntity.description,
                communityEntity.celebrity.name,
                communityEntity.url
            )
        )
            .from(communityEntity)
            .fetch()
}
