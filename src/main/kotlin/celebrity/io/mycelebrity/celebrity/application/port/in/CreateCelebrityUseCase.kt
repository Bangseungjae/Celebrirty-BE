package celebrity.io.mycelebrity.celebrity.application.port.`in`

import celebrity.io.mycelebrity.celebrity.application.dto.RequestCelebrity
import org.springframework.web.multipart.MultipartFile

interface CreateCelebrityUseCase {
    fun createCelebrity(
        multipartFile: MultipartFile,
        request: RequestCelebrity,
        groupId: Long,
    ): Long

}
