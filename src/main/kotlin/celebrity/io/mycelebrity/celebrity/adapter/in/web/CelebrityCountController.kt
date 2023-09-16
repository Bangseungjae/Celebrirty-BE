package celebrity.io.mycelebrity.celebrity.adapter.`in`.web

import celebrity.io.mycelebrity.celebrity.application.dto.RequestUpCount
import celebrity.io.mycelebrity.celebrity.application.port.`in`.UpViewCountUseCase
import celebrity.io.mycelebrity.common.annotation.WebAdapter
import celebrity.io.mycelebrity.common.response.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class CelebrityCountController(
    private val upViewCountUseCase: UpViewCountUseCase,
) {

    @PostMapping("/api/celebrity/count")
    fun upViewCount(
        @RequestBody @Valid requestUpCount: RequestUpCount,
    ): ResponseEntity<ApiResponse<String>> {

        upViewCountUseCase.upCelebrityCount(
            requestUpCount = requestUpCount
        )
        return ResponseEntity
            .ok()
            .body(ApiResponse("OK"))
    }
}
