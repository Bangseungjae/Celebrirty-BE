package celebrity.io.mycelebrity.common.exception.error

import org.springframework.http.HttpStatus
import celebrity.io.mycelebrity.common.exception.common.BaseException

enum class CommonTypeException(
    private val code: Int,
    private val message: String,
    private val status: HttpStatus
) : BaseException {
    INVALID_EMAIL(400, "잘못된 이메일 형식입니다", HttpStatus.BAD_REQUEST),
    NOT_EXISTS(400, "유저가 존재하지 않습니다", HttpStatus.BAD_REQUEST),
    BAD_REQUEST(400, "잘못된 요청입니다", HttpStatus.BAD_REQUEST),
    INVALID_CELEBRITY(400, "존재하지 않는 셀럽입니다", HttpStatus.BAD_REQUEST),
    EXIST_USER(400, "이미 존재하는 유저입니다", HttpStatus.BAD_REQUEST);


    override fun getCode(): Int = code

    override fun getMessage(): String = message

    override fun getStatus(): HttpStatus = status
}
