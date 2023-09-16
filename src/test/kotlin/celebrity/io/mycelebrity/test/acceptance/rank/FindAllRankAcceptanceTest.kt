package celebrity.io.mycelebrity.test.acceptance.rank

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityGroupEntity
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityEntityJpaRepository
import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.repository.CelebrityGroupEntityJpaRepository
import celebrity.io.mycelebrity.celebrity.application.dto.RequestUpCount
import celebrity.io.mycelebrity.celebrity.application.port.`in`.UpViewCountUseCase
import celebrity.io.mycelebrity.common.container.AcceptanceTestBase
import celebrity.io.mycelebrity.common.helper.ResponseFieldUtils
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

@DisplayName("전체 랭킹 조회")
class FindAllRankAcceptanceTest(
    private val celebrityEntityJpaRepository: CelebrityEntityJpaRepository,
    private val celebrityGroupEntityJpaRepository: CelebrityGroupEntityJpaRepository,
    private val upViewCountUseCase: UpViewCountUseCase,
) : AcceptanceTestBase() {

    @Test
    fun `모든 랭킹을 조회하면 상태코드 200을 반환한다`() {
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
        celebrityEntityJpaRepository.save(celebrity2)
        celebrityEntityJpaRepository.save(celebrity3)

        for (i in 1..10) {
            upViewCountUseCase.upCelebrityCount(
                RequestUpCount(
                    from = "222",
                    to = haniId,
                )
            )
        }

        given(documentationSpec)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(
                document(
                    "find-all-rank",
                    responseFieldsSnippet(),
                )
            )
            .`when`()
            .get("/api/rank")
            .then().statusCode(HttpStatus.OK.value())
            .log().body().log().all()
}

    private fun responseFieldsSnippet(): ResponseFieldsSnippet =
        ResponseFieldUtils.createResponseFieldSnippet(
            PayloadDocumentation.fieldWithPath("data[].celebrityId").type(JsonFieldType.NUMBER).description("셀럽 id"),
            PayloadDocumentation.fieldWithPath("data[].rank").type(JsonFieldType.NUMBER).description("순위"),
            PayloadDocumentation.fieldWithPath("data[].rankMovement").type(JsonFieldType.NUMBER).description("이전 랭킹 id"),
            PayloadDocumentation.fieldWithPath("data[].viewCount").type(JsonFieldType.NUMBER).description("조회수"),
            PayloadDocumentation.fieldWithPath("data[].celebrityDescription").type(JsonFieldType.STRING).description("셀럽 설명"),
            PayloadDocumentation.fieldWithPath("data[].celebrityName").type(JsonFieldType.STRING).description("셀럽 이름"),
        )
}
