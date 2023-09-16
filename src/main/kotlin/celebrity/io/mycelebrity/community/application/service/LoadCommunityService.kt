package celebrity.io.mycelebrity.community.application.service

import celebrity.io.mycelebrity.common.annotation.UseCase
import celebrity.io.mycelebrity.common.s3.application.S3Command
import celebrity.io.mycelebrity.community.application.dto.CommunityRequest
import celebrity.io.mycelebrity.community.application.port.`in`.LoadCommunityUseCase
import celebrity.io.mycelebrity.community.application.port.out.LoadCommunity
import celebrity.io.mycelebrity.community.domain.Community
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@UseCase
class LoadCommunityService(
    private val loadCommunity: LoadCommunity,
    private val s3Command: S3Command,
) : LoadCommunityUseCase {


    private val logger = LoggerFactory.getLogger(LoadCommunityService::class.java)

    @Transactional
    override fun loadCommunity(
        communityRequest: CommunityRequest,
        multipartFile: MultipartFile
    ) {
        logger.info("upload file start")
        val url: String = s3Command.uploadFile(file = multipartFile)
        logger.info("upload file finish url=$url")
        val community = Community(
            celebrityId = communityRequest.celebrityId,
            description = communityRequest.description,
            url = url,
        )
        loadCommunity.loadCommunity(community)
    }

}
