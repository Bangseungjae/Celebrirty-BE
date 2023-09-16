package celebrity.io.mycelebrity.community.adapter.`in`.web

import celebrity.io.mycelebrity.common.annotation.WebAdapter
import celebrity.io.mycelebrity.common.domain.Id
import celebrity.io.mycelebrity.common.response.ApiResponse
import celebrity.io.mycelebrity.community.application.dto.CommentRequest
import celebrity.io.mycelebrity.community.application.port.`in`.LoadCommentUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@WebAdapter
@RestController
class WriteCommentController(
    private val loadCommentUseCase: LoadCommentUseCase
) {

    @PostMapping("/api/comment/{id}")
    fun writeComment(
        @PathVariable("id") id: Long,
        @RequestBody commentRequest: CommentRequest,
    ): ResponseEntity<ApiResponse<String>> {
        loadCommentUseCase.writeComment(
            Id(id),
            commentRequest,
        )
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse(
                "댓글 감사용~",
                HttpStatus.CREATED,
            ))
    }
}
