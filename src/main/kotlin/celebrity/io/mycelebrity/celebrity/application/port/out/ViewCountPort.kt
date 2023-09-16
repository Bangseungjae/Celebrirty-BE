package celebrity.io.mycelebrity.celebrity.application.port.out

import celebrity.io.mycelebrity.celebrity.domain.ViewCount

interface ViewCountPort {

    fun loadViewCount(viewCount: ViewCount): Unit
}
