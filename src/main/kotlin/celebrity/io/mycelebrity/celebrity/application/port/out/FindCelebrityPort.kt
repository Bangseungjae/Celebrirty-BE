package celebrity.io.mycelebrity.celebrity.application.port.out

import celebrity.io.mycelebrity.celebrity.adapter.`in`.web.ResponseCelebrity
import celebrity.io.mycelebrity.celebrity.domain.Celebrity

interface FindCelebrityPort {

    fun findAllCelebrity(): List<ResponseCelebrity>
}
