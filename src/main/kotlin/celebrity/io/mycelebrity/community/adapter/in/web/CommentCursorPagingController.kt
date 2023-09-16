package celebrity.io.mycelebrity.community.adapter.`in`.web

import celebrity.io.mycelebrity.common.annotation.WebAdapter
import celebrity.io.mycelebrity.common.dto.Cursor
import celebrity.io.mycelebrity.common.response.ApiResponse
import celebrity.io.mycelebrity.community.application.port.`in`.CommentCursorPagingUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Nullable

@WebAdapter
@RestController
class CommentCursorPagingController(
    private val commentCursorPagingUseCase: CommentCursorPagingUseCase,
) {

    @GetMapping("/api/comment/paging/{communityId}")
    fun cursorPagingAsc(
        @RequestParam @Nullable next: Long?,
        @RequestParam @Nullable size: Long?,
        @PathVariable("communityId") communityId: Long
    ): ResponseEntity<ApiResponse<*>> {
        val findByCursor = commentCursorPagingUseCase.findByCursor(
            cursor = Cursor(
                next = next,
                size = size,
            ),
            communityId = communityId
        )
        return ResponseEntity
            .ok()
            .body(ApiResponse(findByCursor))
    }
}
