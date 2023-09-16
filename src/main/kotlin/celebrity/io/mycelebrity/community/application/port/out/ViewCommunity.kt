package celebrity.io.mycelebrity.community.application.port.out

import celebrity.io.mycelebrity.community.application.dto.CommunityResponse

interface ViewCommunity {
    fun getAllCommunity(): List<CommunityResponse>
}
