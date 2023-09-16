package celebrity.io.mycelebrity.community.application.dto

import jakarta.validation.constraints.NotEmpty

data class CommunityRequest(
    @NotEmpty
    val celebrityId: Long,

    @NotEmpty
    val description: String,

) {
}
