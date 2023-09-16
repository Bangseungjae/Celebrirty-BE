package celebrity.io.mycelebrity.celebrity.application.dto

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import jakarta.validation.constraints.NotBlank

data class RequestCelebrity(
    @NotBlank
    val celebrityName: String,
    @NotBlank
    val celebrityDescription: String,
    @NotBlank
    val communityDescription: String,
)

