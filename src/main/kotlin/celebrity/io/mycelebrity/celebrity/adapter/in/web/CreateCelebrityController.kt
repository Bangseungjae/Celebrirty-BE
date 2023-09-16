package celebrity.io.mycelebrity.celebrity.adapter.`in`.web

import celebrity.io.mycelebrity.celebrity.application.dto.RequestCelebrity
import celebrity.io.mycelebrity.celebrity.application.port.`in`.CreateCelebrityUseCase
import celebrity.io.mycelebrity.common.annotation.WebAdapter
import celebrity.io.mycelebrity.common.response.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@WebAdapter
@RestController
class CreateCelebrityController(
    private val createCelebrityUseCase: CreateCelebrityUseCase
){

    @PostMapping("/api/celebrity/{groupId}")
    fun createCelebrity(
        @RequestPart("file") multipartFile: MultipartFile,
        @RequestPart("request") @Valid request: RequestCelebrity,
        @PathVariable("groupId") groupId: Long,
    ): ResponseEntity<ApiResponse<Long>> {
        val celebrityId = createCelebrityUseCase.createCelebrity(
            multipartFile = multipartFile,
            request = request,
            groupId = groupId
        )
        return ResponseEntity
            .ok()
            .body(ApiResponse(celebrityId))
    }
}
