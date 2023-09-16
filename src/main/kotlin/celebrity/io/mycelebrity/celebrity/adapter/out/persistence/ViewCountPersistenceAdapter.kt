package celebrity.io.mycelebrity.celebrity.adapter.out.persistence

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityHistoryEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityEntityJpaRepository
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityHistoryJpaRepository
import celebrity.io.mycelebrity.celebrity.application.port.out.ViewCountPort
import celebrity.io.mycelebrity.celebrity.domain.ViewCount
import celebrity.io.mycelebrity.common.annotation.PersistenceAdapter
import celebrity.io.mycelebrity.common.exception.common.CelebrityException
import celebrity.io.mycelebrity.common.exception.error.CommonTypeException
import org.slf4j.LoggerFactory

@PersistenceAdapter
class ViewCountPersistenceAdapter(
    private val celebrityEntityJpaRepository: CelebrityEntityJpaRepository,
    private val celebrityHistoryJpaRepository: CelebrityHistoryJpaRepository,
) : ViewCountPort {

    private val logger = LoggerFactory.getLogger(ViewCountPersistenceAdapter::class.java)

    override fun loadViewCount(viewCount: ViewCount) {
        logger.info("count from=${viewCount.from}, to=${viewCount.to}")
        val celebrityEntity = celebrityEntityJpaRepository.findById(viewCount.to)
            .orElseThrow { CelebrityException(CommonTypeException.INVALID_CELEBRITY) }

        val celebrityHistoryEntity = CelebrityHistoryEntity(
            from = viewCount.from,
            to = celebrityEntity
        )
        celebrityHistoryJpaRepository.save(celebrityHistoryEntity)
    }
}
