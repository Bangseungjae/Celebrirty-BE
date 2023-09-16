package celebrity.io.mycelebrity.celebrity.application.port.`in`

import celebrity.io.mycelebrity.celebrity.application.dto.RequestUpCount

interface UpViewCountUseCase {

    fun upCelebrityCount(requestUpCount: RequestUpCount): Unit
}
