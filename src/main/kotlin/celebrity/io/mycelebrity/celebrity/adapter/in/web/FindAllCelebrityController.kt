package celebrity.io.mycelebrity.celebrity.adapter.`in`.web

import celebrity.io.mycelebrity.celebrity.application.port.`in`.FindAllCelebrityUseCase
import celebrity.io.mycelebrity.common.annotation.WebAdapter
import celebrity.io.mycelebrity.common.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@WebAdapter
class FindAllCelebrityController(
    private val findAllCelebrityUseCase: FindAllCelebrityUseCase,
) {

    @GetMapping("/api/celebrity")
    fun findAllCelebrity(): ResponseEntity<ApiResponse<List<ResponseCelebrity>>> =
        findAllCelebrityUseCase.findAllCelebrity()
            .let {
                ResponseEntity
                    .ok()
                    .body(ApiResponse(it))
            }
}
