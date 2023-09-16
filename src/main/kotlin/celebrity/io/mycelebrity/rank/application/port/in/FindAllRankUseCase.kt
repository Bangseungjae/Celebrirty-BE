package celebrity.io.mycelebrity.rank.application.port.`in`

import celebrity.io.mycelebrity.rank.application.dto.RankResponse

interface FindAllRankUseCase {
    fun findAllRank(): List<RankResponse>
}
