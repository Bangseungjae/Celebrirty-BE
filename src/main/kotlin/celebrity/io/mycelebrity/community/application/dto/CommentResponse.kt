package celebrity.io.mycelebrity.community.application.dto

import celebrity.io.mycelebrity.common.persistence.entity.DATE_PATTERN
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime

data class CommentResponse(
    val id: Long,
    val comment: String,
    @get:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = DATE_PATTERN
    )
    val createdAt: ZonedDateTime
)
