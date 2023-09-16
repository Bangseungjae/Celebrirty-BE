package celebrity.io.mycelebrity.common.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import celebrity.io.mycelebrity.common.exception.common.ErrorResponse
import celebrity.io.mycelebrity.common.exception.common.CelebrityException
import java.lang.RuntimeException
import kotlin.IllegalArgumentException

@RestControllerAdvice
class ExceptionHandler {
    private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException::class)
    fun catchIllegalArgumentException(exception: IllegalArgumentException): ErrorResponse =
        let {
            logger.info("IllegalArgumentException: ${exception.message}")
            ErrorResponse(HttpStatus.BAD_REQUEST, exception.message)
        }

    @org.springframework.web.bind.annotation.ExceptionHandler(CelebrityException::class)
    fun catchPloPloException(exception: CelebrityException): ErrorResponse =
        let {
            logger.info("PloPloException: ${exception.message}")
            ErrorResponse(
                message = exception.message,
                status = HttpStatus.BAD_REQUEST
            )
        }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException::class)
    fun catchRuntimeException(exception: RuntimeException): ErrorResponse =
        let {
            logger.info("RuntimeException: ${exception.message}")
            ErrorResponse(
                message = exception.message,
                status = HttpStatus.BAD_REQUEST
            )
        }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception::class)
    fun catchException(exception: Exception): ErrorResponse =
        let {
            logger.info("Exception: ${exception.message}")
            ErrorResponse(
                message = exception.message,
                status = HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
}
