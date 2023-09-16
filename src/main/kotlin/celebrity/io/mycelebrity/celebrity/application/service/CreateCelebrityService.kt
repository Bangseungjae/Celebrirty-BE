package celebrity.io.mycelebrity.celebrity.application.service

import celebrity.io.mycelebrity.celebrity.application.dto.RequestCelebrity
import celebrity.io.mycelebrity.celebrity.application.port.`in`.CreateCelebrityUseCase
import celebrity.io.mycelebrity.celebrity.application.port.out.CreateCelebrityPort
import celebrity.io.mycelebrity.common.annotation.UseCase
import celebrity.io.mycelebrity.community.application.dto.CommunityRequest
import celebrity.io.mycelebrity.community.application.port.`in`.LoadCommunityUseCase
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile

@UseCase
class CreateCelebrityService(
    private val loadCommunityUseCase: LoadCommunityUseCase,
    private val createCelebrityPort: CreateCelebrityPort
) : CreateCelebrityUseCase {
    private val logger = LoggerFactory.getLogger(CreateCelebrityService::class.java)

    override fun createCelebrity(multipartFile: MultipartFile, request: RequestCelebrity, groupId: Long): Long {
        val celebrityId = createCelebrityPort.createCelebrity(
            request = request,
            groupId = groupId,
        )
        logger.info("셀럽 저장 끝")
        logger.info("셀럽과 관련된 커뮤니티 생성 시작")
        loadCommunityUseCase.loadCommunity(
            communityRequest = CommunityRequest(
                celebrityId = celebrityId,
                description = request.communityDescription,
            ),
            multipartFile = multipartFile,
        )
        logger.info("셀럽과 관련된 커뮤니티 생성 끝")
        return celebrityId
    }
}
