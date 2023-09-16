package celebrity.io.mycelebrity.common.exception.common

import org.springframework.http.HttpStatus

interface BaseException {

    fun getCode(): Int
    fun getMessage(): String
    fun getStatus(): HttpStatus
}
