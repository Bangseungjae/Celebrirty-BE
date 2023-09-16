package celebrity.io.mycelebrity.rank.application.port.out

import celebrity.io.mycelebrity.rank.domain.BeforeRank

interface FindBeforeRank {
    fun findBeforeRank(): List<BeforeRank>
}
