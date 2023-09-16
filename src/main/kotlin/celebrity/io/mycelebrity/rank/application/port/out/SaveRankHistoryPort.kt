package celebrity.io.mycelebrity.rank.application.port.out

import celebrity.io.mycelebrity.rank.domain.BeforeRank
import celebrity.io.mycelebrity.rank.domain.Rank

interface SaveRankHistoryPort {
    fun saveRankHistory(rank: Rank): Unit
}
