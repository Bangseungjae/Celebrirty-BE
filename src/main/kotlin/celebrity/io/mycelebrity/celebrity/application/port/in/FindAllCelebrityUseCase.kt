package celebrity.io.mycelebrity.celebrity.application.port.`in`

import celebrity.io.mycelebrity.celebrity.adapter.`in`.web.ResponseCelebrity
import celebrity.io.mycelebrity.celebrity.domain.Celebrity

interface FindAllCelebrityUseCase {
    fun findAllCelebrity(): List<ResponseCelebrity>
}
