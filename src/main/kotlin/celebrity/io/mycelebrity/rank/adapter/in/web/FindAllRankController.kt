package celebrity.io.mycelebrity.rank.adapter.`in`.web

import celebrity.io.mycelebrity.common.annotation.WebAdapter
import celebrity.io.mycelebrity.common.response.ApiResponse
import celebrity.io.mycelebrity.rank.application.dto.RankResponse
import celebrity.io.mycelebrity.rank.application.port.`in`.FindAllRankUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class FindAllRankController(
    private val findAllRankUseCase: FindAllRankUseCase
) {

    @GetMapping("/api/rank")
    fun findAllRank(): ResponseEntity<ApiResponse<List<RankResponse>>> {
        val allRank = findAllRankUseCase.findAllRank()
        return ResponseEntity
            .ok()
            .body(ApiResponse(allRank))
    }
}
