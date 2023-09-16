package celebrity.io.mycelebrity.community.adapter.`in`.web

import celebrity.io.mycelebrity.common.annotation.WebAdapter
import celebrity.io.mycelebrity.common.response.ApiResponse
import celebrity.io.mycelebrity.community.application.dto.CommunityRequest
import celebrity.io.mycelebrity.community.application.port.`in`.LoadCommunityUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@WebAdapter
@RestController
class CreateCommunityController(
    private val loadCommunityUseCase: LoadCommunityUseCase
) {

    @PostMapping("/api/community", consumes = [MULTIPART_FORM_DATA_VALUE])
    fun createCommunity(
        @RequestPart("file") multipartFile: MultipartFile,
        @RequestPart @Valid communityRequest: CommunityRequest,
    ): ResponseEntity<ApiResponse<String>> {
        loadCommunityUseCase.loadCommunity(
            communityRequest = communityRequest,
            multipartFile = multipartFile
        )
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    data = "감사합니다.",
                    status = HttpStatus.CREATED,
                )
            )
    }
}
