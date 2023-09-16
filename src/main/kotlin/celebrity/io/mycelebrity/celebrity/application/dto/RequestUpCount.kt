package celebrity.io.mycelebrity.celebrity.application.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class RequestUpCount(
    @NotBlank
    val from: String,

    @Min(value = 1, message = "to 값은 최소 1 이상입니다.")
    val to: Long,
)
