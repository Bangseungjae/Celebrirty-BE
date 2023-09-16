package celebrity.io.mycelebrity.common.response

import celebrity.io.mycelebrity.common.persistence.entity.DATE_PATTERN
import celebrity.io.mycelebrity.common.persistence.entity.KR_LOCATION
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.ZoneId
import java.time.ZonedDateTime

data class ApiResponse <T> private constructor(

    @get:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = DATE_PATTERN
    )
    val eventTime: ZonedDateTime = ZonedDateTime.now(ZoneId.of(KR_LOCATION)),
    var status: HttpStatus = HttpStatus.OK,
    var code: Int = HttpStatus.OK.value(),
    val data: T,
)  {

    companion object {
        operator fun <T> invoke(data: T): ApiResponse<T> = ApiResponse(
            data = data
        )

        operator fun <T> invoke(
            data: T,
            status: HttpStatus
        ): ApiResponse<T> = ApiResponse(
            data = data,
            status = status,
            code = status.value(),
        )
    }
}
