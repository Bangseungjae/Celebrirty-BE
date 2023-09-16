package celebrity.io.mycelebrity.test.acceptance.celebrity

import celebrity.io.mycelebrity.common.container.AcceptanceTestBase
import celebrity.io.mycelebrity.common.helper.ResponseFieldUtils.createResponseFieldSnippet
import celebrity.io.mycelebrity.common.helper.celebrity.CelebrityUtils
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

@DisplayName("전체 셀럽 조회 테스트")
class FindAllCelebrityAcceptanceTest(
    private val celebrityUtils: CelebrityUtils,
) : AcceptanceTestBase(){

    @Test
    fun `전체 셀럽 조회 status=OK`() {
        celebrityUtils.generateCelebrity()
        val uri = "/api/celebrity"
        given(documentationSpec)
            .contentType(ContentType.JSON)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .filter(
                document(
                    "find-all-celebrity",
                    responseFieldsSnippet(),
                )
            ).`when`()
            .get(uri)
            .then().statusCode(HttpStatus.OK.value())
            .log().body().log().all()
    }

    private fun responseFieldsSnippet(): ResponseFieldsSnippet =
        createResponseFieldSnippet(
            PayloadDocumentation.fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("셀럽 index"),
            PayloadDocumentation.fieldWithPath("data[].name").type(JsonFieldType.STRING).description("셀럽 이름"),
            PayloadDocumentation.fieldWithPath("data[].group").type(JsonFieldType.STRING).description("셀럽 그룹"),
            PayloadDocumentation.fieldWithPath("data[].description").type(JsonFieldType.STRING).description("셀럽 설명"),
            PayloadDocumentation.fieldWithPath("data[].url").type(JsonFieldType.STRING).description("셀럽 사진 url"),
        )
}
