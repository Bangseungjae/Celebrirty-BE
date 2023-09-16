package celebrity.io.mycelebrity.test.unit

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityGroupEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityEntityJpaRepository
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityGroupEntityJpaRepository
import celebrity.io.mycelebrity.celebrity.application.dto.RequestUpCount
import celebrity.io.mycelebrity.celebrity.application.port.`in`.UpViewCountUseCase
import celebrity.io.mycelebrity.common.container.AcceptanceTestBase
import celebrity.io.mycelebrity.rank.application.port.out.FindAllRankPort
import celebrity.io.mycelebrity.rank.domain.Rank
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.stream.LongStream
import kotlin.math.min

@DisplayName("랭킹 구하기 단위 테스트")
class RankTest(
    private val celebrityEntityJpaRepository: CelebrityEntityJpaRepository,
    private val findAllRankPort: FindAllRankPort,
    private val celebrityGroupEntityJpaRepository: CelebrityGroupEntityJpaRepository,
    private val upViewCountUseCase: UpViewCountUseCase,
): AcceptanceTestBase() {

    @Test
    fun `두개의 셀럽이 있고 순위를 반환한다`() {
        val celebrityGroupEntity = CelebrityGroupEntity(
            name = "newJeans",
        )
        val groupEntity = celebrityGroupEntityJpaRepository.save(celebrityGroupEntity)
        val celebrity1 = CelebrityEntity(
            name = "HANI",
            description = "cute",
            group = groupEntity,
        )
        val celebrity2 = CelebrityEntity(
            name = "MINJI",
            description = "cool",
            group = groupEntity,
        )
        val celebrity3 = CelebrityEntity(
            name = "HEARIN",
            description = "cat",
            group = groupEntity,
        )
        val haniId = celebrityEntityJpaRepository.save(celebrity1).id!!
        val minjiId = celebrityEntityJpaRepository.save(celebrity2).id!!
        val hearinId = celebrityEntityJpaRepository.save(celebrity3).id!!
        for (i in 1..10) {
            upViewCountUseCase.upCelebrityCount(
                RequestUpCount(
                    from = "222",
                    to = haniId,
                )
            )
        }
        for (i in 1..5) {
            upViewCountUseCase.upCelebrityCount(
                RequestUpCount(
                    from = "111",
                    to = minjiId,
                )
            )
        }
        for (i in 1..6) {
            upViewCountUseCase.upCelebrityCount(
                RequestUpCount(
                    from = "222",
                    to = hearinId,
                )
            )
        }
        val findAllRank: List<Rank> = findAllRankPort.findAllRank()
        val result: Rank? = findAllRank.find { it.celebrityId == haniId }
        result?.rank shouldBe 1L
        result?.celebrityId shouldBe haniId
        result?.celebrityDescription shouldBe "cute"
        val result2: Rank? = findAllRank.find { it.celebrityId == hearinId }
        result2?.rank shouldBe 2L
    }
}
