package celebrity.io.mycelebrity.test.acceptance.community

import celebrity.io.mycelebrity.common.container.AcceptanceTestBase
import celebrity.io.mycelebrity.common.helper.ResponseFieldUtils
import celebrity.io.mycelebrity.common.helper.community.CommunityUtils
import com.google.gson.JsonObject
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
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

@DisplayName("커뮤니티 댓글 쓰기 인수 테스트")
class WriteCommentAcceptanceTest(
    private val communityUtils: CommunityUtils,
): AcceptanceTestBase() {

    @Test
    fun `커뮤니티에 글을 저장하면 Status Code=201`() {
        val communityId = communityUtils.generateCommunity()
        val jsonObject = getRequestCommunityContent()

        given(documentationSpec)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(jsonObject.toString())
            .filter(
                document(
                    "write-comment",
                    requestFieldsSnippet(),
                    responseFieldsSnippet(),
                    pathParametersSnippet(),
                )
            )
            .`when`()
            .post("/api/comment/{communityId}", communityId)
            .then().statusCode(HttpStatus.CREATED.value())
            .log().body().log().all()

    }

    private fun pathParametersSnippet(): PathParametersSnippet? = pathParameters(
        parameterWithName("communityId").description("community 인덱스 값")
    )

    private fun getRequestCommunityContent(): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("comment", "content")
        return jsonObject
    }

    private fun responseFieldsSnippet(): ResponseFieldsSnippet =
        ResponseFieldUtils.createResponseFieldSnippet(
            PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.STRING).description("글이 저장되었습니다."),
        )

    private fun requestFieldsSnippet(): RequestFieldsSnippet =
        requestFields(
            fieldWithPath("comment").type(JsonFieldType.STRING).description("댓글")
        )

}
