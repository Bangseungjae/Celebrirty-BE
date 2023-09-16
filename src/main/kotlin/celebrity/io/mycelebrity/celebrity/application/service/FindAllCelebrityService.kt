package celebrity.io.mycelebrity.celebrity.application.service

import celebrity.io.mycelebrity.celebrity.adapter.`in`.web.ResponseCelebrity
import celebrity.io.mycelebrity.celebrity.application.port.`in`.FindAllCelebrityUseCase
import celebrity.io.mycelebrity.celebrity.application.port.out.FindCelebrityPort
import celebrity.io.mycelebrity.celebrity.domain.Celebrity
import celebrity.io.mycelebrity.common.annotation.UseCase

@UseCase
class FindAllCelebrityService(
    private val findCelebrityPort: FindCelebrityPort,
) : FindAllCelebrityUseCase {
    override fun findAllCelebrity(): List<ResponseCelebrity> =
        findCelebrityPort.findAllCelebrity()
}
