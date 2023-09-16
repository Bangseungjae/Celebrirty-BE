package celebrity.io.mycelebrity.common.exception.common

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ErrorResponse private constructor(
    val eventTime: LocalDateTime = LocalDateTime.now(),
    var status: HttpStatus = HttpStatus.BAD_REQUEST,
    var code: Int = HttpStatus.BAD_REQUEST.value(),
    var message: String?,
) {
    companion object {
        operator fun invoke(
            message: String?,
        ): ErrorResponse = ErrorResponse(
            message = message
        )

        operator fun invoke(
            status: HttpStatus,
            message: String?,
        ): ErrorResponse = ErrorResponse(
            status = status,
            code = status.value(),
            message = message,
        )
    }
}
