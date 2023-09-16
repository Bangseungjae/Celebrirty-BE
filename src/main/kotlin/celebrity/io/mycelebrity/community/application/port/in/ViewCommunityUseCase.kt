package celebrity.io.mycelebrity.community.application.port.`in`

import celebrity.io.mycelebrity.community.application.dto.CommunityResponse

interface ViewCommunityUseCase {

    fun getAllCommunity(): List<CommunityResponse>
}
