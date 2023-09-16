package celebrity.io.mycelebrity.rank.adapter.out.persistence

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityHistoryEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.QCelebrityEntity.celebrityEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.QCelebrityHistoryEntity.celebrityHistoryEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityEntityJpaRepository
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityHistoryJpaRepository
import celebrity.io.mycelebrity.common.annotation.PersistenceAdapter
import celebrity.io.mycelebrity.common.persistence.entity.KR_LOCATION
import celebrity.io.mycelebrity.rank.adapter.out.persistence.entity.QRankHistoryEntity
import celebrity.io.mycelebrity.rank.adapter.out.persistence.entity.QRankHistoryEntity.rankHistoryEntity
import celebrity.io.mycelebrity.rank.adapter.out.persistence.entity.RankHistoryEntity
import celebrity.io.mycelebrity.rank.adapter.out.persistence.repository.RankHistoryJpaRepository
import celebrity.io.mycelebrity.rank.application.port.out.FindAllRankPort
import celebrity.io.mycelebrity.rank.application.port.out.FindBeforeRank
import celebrity.io.mycelebrity.rank.application.port.out.SaveRankHistoryPort
import celebrity.io.mycelebrity.rank.domain.BeforeRank
import celebrity.io.mycelebrity.rank.domain.Rank
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@PersistenceAdapter
class RankPersistenceAdapter(
    private val rankHistoryJpaRepository: RankHistoryJpaRepository,
    private val celebrityHistoryJpaRepository: CelebrityHistoryJpaRepository,
    private val queryFactory: JPAQueryFactory,
    private val celebrityEntityJpaRepository: CelebrityEntityJpaRepository,
) :
    FindAllRankPort,
    SaveRankHistoryPort,
    FindBeforeRank {

    private val logger = LoggerFactory.getLogger(RankPersistenceAdapter::class.java)
    override fun findAllRank(): List<Rank> {
        val dateTime: ZonedDateTime = ZonedDateTime.now(ZoneId.of(KR_LOCATION)).minusDays(1L)
        val ranks: MutableList<Rank> = queryFactory
            .select(
                Projections.constructor(
                    Rank::class.java,
                    celebrityEntity.id,
                    celebrityEntity.description,
                    celebrityHistoryEntity.id.count(),
                    celebrityEntity.name,
                )
            )
            .from(celebrityEntity)
            .leftJoin(celebrityEntity.history, celebrityHistoryEntity)
            .on(celebrityHistoryEntity.createdAt.gt(dateTime))
            .groupBy(celebrityEntity.id)
            .orderBy(celebrityHistoryEntity.id.count().desc())
            .fetch()
        ranks.forEachIndexed { index, rank ->
            rank.rank = index + 1L
        }
        return ranks
    }

    override fun saveRankHistory(rank: Rank): Unit {
        val celebrityEntity = rank.celebrityId.let {
            celebrityEntityJpaRepository.findById(it)
                .orElseThrow { IllegalArgumentException("해당 id의 셀럽은 존재하지 않습니다") }
        }

        val rankHistoryEntity = RankHistoryEntity(
            celebrity = celebrityEntity,
            beforeRank = rank.rank,
        )

        logger.info("celebrityId: ${rank.celebrityId} rank: ${rank.rank}")
        rankHistoryJpaRepository.findByCelebrity(celebrityEntity)
            ?.let {
                logger.info("rank history update")
                queryFactory.update(QRankHistoryEntity.rankHistoryEntity)
                    .set(QRankHistoryEntity.rankHistoryEntity.beforeRank, rank.rank)
                    .where(QRankHistoryEntity.rankHistoryEntity.celebrity.eq(celebrityEntity))
                    .execute()
            }
            ?: {
                logger.info("rank save start")
                rankHistoryJpaRepository.save(rankHistoryEntity)
            }
    }

    override fun findBeforeRank(): List<BeforeRank> {
        return queryFactory.select(
            Projections.constructor(
                BeforeRank::class.java,
                rankHistoryEntity.celebrity.id,
                rankHistoryEntity.beforeRank,
            )
        )
            .from(rankHistoryEntity)
            .leftJoin(rankHistoryEntity.celebrity, celebrityEntity)
            .fetch()
    }
}
