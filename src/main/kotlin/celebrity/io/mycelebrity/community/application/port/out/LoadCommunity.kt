package celebrity.io.mycelebrity.community.application.port.out

import celebrity.io.mycelebrity.community.domain.Community

interface LoadCommunity {

    fun loadCommunity(community: Community)
}
