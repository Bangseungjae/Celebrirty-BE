package celebrity.io.mycelebrity.community.application.port.`in`

import celebrity.io.mycelebrity.community.application.dto.CommunityRequest
import org.springframework.web.multipart.MultipartFile

interface LoadCommunityUseCase {
    fun loadCommunity(
        communityRequest: CommunityRequest,
        multipartFile: MultipartFile,
        )
}
