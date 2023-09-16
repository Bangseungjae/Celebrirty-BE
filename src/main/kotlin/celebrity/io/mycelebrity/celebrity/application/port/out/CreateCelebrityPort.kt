package celebrity.io.mycelebrity.celebrity.application.port.out

import celebrity.io.mycelebrity.celebrity.application.dto.RequestCelebrity

interface CreateCelebrityPort {
    fun createCelebrity(request: RequestCelebrity, groupId: Long): Long
}
