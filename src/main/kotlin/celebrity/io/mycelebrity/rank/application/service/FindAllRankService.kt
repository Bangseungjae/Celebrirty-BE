package celebrity.io.mycelebrity.rank.application.service

import celebrity.io.mycelebrity.common.annotation.UseCase
import celebrity.io.mycelebrity.rank.application.dto.RankResponse
import celebrity.io.mycelebrity.rank.application.port.`in`.FindAllRankUseCase
import celebrity.io.mycelebrity.rank.application.port.out.FindAllRankPort
import celebrity.io.mycelebrity.rank.application.port.out.FindBeforeRank
import celebrity.io.mycelebrity.rank.application.port.out.SaveRankHistoryPort
import celebrity.io.mycelebrity.rank.domain.BeforeRank
import celebrity.io.mycelebrity.rank.domain.Rank
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

@UseCase
class FindAllRankService(
    private val findAllRankPort: FindAllRankPort,
    private val saveRankHistoryPort: SaveRankHistoryPort,
    private val findBeforeRank: FindBeforeRank,
) : FindAllRankUseCase {

    private val logger = LoggerFactory.getLogger(FindAllRankService::class.java)

    @Transactional
    override fun findAllRank(): List<RankResponse> {

        val allRank: List<Rank> = findAllRankPort.findAllRank()
        logger.info(allRank.toString())
        val beforeRank: List<BeforeRank> = findBeforeRank.findBeforeRank()
        logger.info("findBeforeRank finish")
        logger.info(beforeRank.toString())
        val rankResponseList: MutableList<RankResponse> = mutableListOf()
        for (rank in allRank) {
            saveRankHistoryPort.saveRankHistory(rank)
            logger.info("save rank history celebrityId=${rank.celebrityId}")
            var rankDifference: Long
            val findRank: Long = beforeRank.find { it.celebrityId == rank.celebrityId }
                ?.let { it.beforeRank }
                ?: rank.rank
            logger.info("beforeRank find finish")
            rankDifference = findRank - rank.rank
            val rankResponse = RankResponse(
                celebrityId = rank.celebrityId,
                rank = rank.rank,
                rankMovement = rankDifference,
                viewCount = rank.viewCount,
                celebrityDescription = rank.celebrityDescription,
                celebrityName = rank.celebrityName,
            )
            rankResponseList.add(rankResponse)
        }
        return rankResponseList
    }
}
