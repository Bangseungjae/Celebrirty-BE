package celebrity.io.mycelebrity.celebrity.application.service

import celebrity.io.mycelebrity.celebrity.application.dto.RequestUpCount
import celebrity.io.mycelebrity.celebrity.application.port.`in`.UpViewCountUseCase
import celebrity.io.mycelebrity.celebrity.application.port.out.ViewCountPort
import celebrity.io.mycelebrity.celebrity.domain.toViewCount
import celebrity.io.mycelebrity.common.annotation.UseCase
import org.slf4j.LoggerFactory

@UseCase
class UpViewCountService(
    private val viewCountPort: ViewCountPort,
) : UpViewCountUseCase {

    private val logger = LoggerFactory.getLogger(UpViewCountService::class.java)

    override fun upCelebrityCount(requestUpCount: RequestUpCount) {
        logger.info("count start")
        viewCountPort.loadViewCount(requestUpCount.toViewCount())
    }
}
