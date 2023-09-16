package celebrity.io.mycelebrity.rank.application.port.out

import celebrity.io.mycelebrity.rank.domain.Rank

interface FindAllRankPort {
    fun findAllRank(): List<Rank>
}
