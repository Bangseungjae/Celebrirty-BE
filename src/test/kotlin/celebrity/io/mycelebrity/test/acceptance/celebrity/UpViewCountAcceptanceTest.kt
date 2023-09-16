package celebrity.io.mycelebrity.test.acceptance.celebrity

import celebrity.io.mycelebrity.common.container.AcceptanceTestBase
import celebrity.io.mycelebrity.common.helper.ResponseFieldUtils
import celebrity.io.mycelebrity.common.helper.celebrity.CelebrityUtils
import com.google.gson.JsonObject
import io.restassured.RestAssured.get
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

@DisplayName("셀럽 조회수 카운팅 인수 테스트")
class UpViewCountAcceptanceTest(
    private val celebrityUtils: CelebrityUtils,
): AcceptanceTestBase() {

    @Test
    fun `up celebrity view count`() {
        val id: Long = celebrityUtils.generateCelebrity()
        val jsonObject = getObject(id)
        given(documentationSpec)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(jsonObject.toString())
            .filter(
                document(
                    "up-celebrity-count",
                    requestFieldsSnippet(),
                    responseFieldsSnippet(),
                )
            )
            .`when`()
            .post("/api/celebrity/count")
            .then()
            .statusCode(HttpStatus.OK.value())
            .log().body().log().all()
    }

    private fun requestFieldsSnippet(): RequestFieldsSnippet? = requestFields(
        fieldWithPath("from").type(JsonFieldType.STRING).description("사용자 MAC주소"),
        fieldWithPath("to").type(JsonFieldType.NUMBER).description("셀럽 id"),
    )

    private fun responseFieldsSnippet(): ResponseFieldsSnippet =
        ResponseFieldUtils.createResponseFieldSnippet(
            PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.STRING).description("OK"),
        )

    private fun getObject(id: Long): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("from", "ss:44:ss:22:ss")
        jsonObject.addProperty("to", id)
        return jsonObject
    }
}
