package celebrity.io.mycelebrity.test.acceptance.community

import celebrity.io.mycelebrity.common.container.AcceptanceTestBase
import celebrity.io.mycelebrity.common.helper.ResponseFieldUtils
import celebrity.io.mycelebrity.common.helper.community.CommunityUtils
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

@DisplayName("모든 커뮤니티 전체 조회 인수 테스트")
class FindAllCommunityAcceptanceTest(
    private val communityUtils: CommunityUtils,
) : AcceptanceTestBase(){

    @Test
    fun `모든 community 조회`() {
        communityUtils.generateCommunity()
        given(documentationSpec)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(
                document(
                    "find-all-community",
                    responseFieldsSnippet(),
                )
            ).`when`()
            .get("/api/community")
            .then()
            .assertThat().statusCode(200)
            .log().body().log().all()
    }

    private fun responseFieldsSnippet(): ResponseFieldsSnippet =
        ResponseFieldUtils.createResponseFieldSnippet(
            PayloadDocumentation.fieldWithPath("data[].communityId").type(JsonFieldType.NUMBER).description("커뮤니티 id"),
            PayloadDocumentation.fieldWithPath("data[].celebrityName").type(JsonFieldType.STRING).description("셀럽 이름"),
            PayloadDocumentation.fieldWithPath("data[].description").type(JsonFieldType.STRING).description("설명"),
            PayloadDocumentation.fieldWithPath("data[].url").type(JsonFieldType.STRING).description("커뮤니티 사진 url"),
        )
}
