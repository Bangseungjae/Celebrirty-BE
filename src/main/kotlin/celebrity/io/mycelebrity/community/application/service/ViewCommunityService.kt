package celebrity.io.mycelebrity.community.application.service

import celebrity.io.mycelebrity.common.annotation.UseCase
import celebrity.io.mycelebrity.community.application.dto.CommunityResponse
import celebrity.io.mycelebrity.community.application.port.`in`.ViewCommunityUseCase
import celebrity.io.mycelebrity.community.application.port.out.ViewCommunity
import org.springframework.transaction.annotation.Transactional

@UseCase
class ViewCommunityService(
    private val viewCommunity: ViewCommunity,
): ViewCommunityUseCase {

    @Transactional(readOnly = true)
    override fun getAllCommunity(): List<CommunityResponse> =
        viewCommunity.getAllCommunity()
}
