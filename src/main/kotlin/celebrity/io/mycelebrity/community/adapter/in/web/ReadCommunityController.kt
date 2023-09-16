package celebrity.io.mycelebrity.community.adapter.`in`.web

import celebrity.io.mycelebrity.common.annotation.WebAdapter
import celebrity.io.mycelebrity.common.response.ApiResponse
import celebrity.io.mycelebrity.community.application.dto.CommunityResponse
import celebrity.io.mycelebrity.community.application.port.`in`.ViewCommunityUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class ReadCommunityController(
    private val viewCommunityUseCase: ViewCommunityUseCase,
) {

    @GetMapping("/api/community")
    fun getAllCommunity(): ResponseEntity<ApiResponse<List<CommunityResponse>>> {
        val allCommunity = viewCommunityUseCase.getAllCommunity()
        return ResponseEntity.ok()
            .body(ApiResponse(allCommunity))
    }
}
