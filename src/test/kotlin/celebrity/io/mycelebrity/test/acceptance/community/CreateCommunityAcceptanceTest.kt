package celebrity.io.mycelebrity.test.acceptance.community

import celebrity.io.mycelebrity.common.container.AcceptanceTestBase
import celebrity.io.mycelebrity.common.helper.ResponseFieldUtils
import celebrity.io.mycelebrity.common.helper.celebrity.CelebrityUtils
import celebrity.io.mycelebrity.community.application.dto.CommunityRequest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.payload.RequestPartFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.request.RequestDocumentation.partWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParts
import org.springframework.restdocs.request.RequestPartsSnippet
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

@DisplayName("커뮤니티 만들기 인수테스트")
class CreateCommunityAcceptanceTest(
    private val celebrityUtils: CelebrityUtils,
) : AcceptanceTestBase(){

    @Test
    fun `create community then status created 201`() {
        val celebrityId: Long = celebrityUtils.insertOnlyCelebrity()
        val fileByte = "Hello, World!".encodeToByteArray()
        val communityRequest = CommunityRequest(description = "test123", celebrityId = celebrityId)
        given(documentationSpec)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .multiPart("communityRequest", communityRequest, MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("file", "file.png", fileByte, MediaType.MULTIPART_FORM_DATA_VALUE)
            .filter(
                document(
                    "create-community",
                    getRequestPartsSnippet(),
                    requestPartFieldsSnippet(),
                    responseFieldsSnippet(),
                )
            )
            .`when`()
            .post("/api/community")
            .then().statusCode(HttpStatus.CREATED.value())
            .log().body().log().all()
    }

    private fun getRequestPartsSnippet(): RequestPartsSnippet? {
        return requestParts(
            partWithName("communityRequest").description("커큐니티"),
            partWithName("file").description("커뮤니티에 넣을 사진")
        )
    }

    private fun requestPartFieldsSnippet(): RequestPartFieldsSnippet? =
        requestPartFields(
            "communityRequest",
            fieldWithPath("celebrityId").type(JsonFieldType.NUMBER).description("셀럽 index"),
            fieldWithPath("description").type(JsonFieldType.STRING).description("해당 셀럽 커뮤니티 설명"),
        )

    private fun responseFieldsSnippet(): ResponseFieldsSnippet =
        ResponseFieldUtils.createResponseFieldSnippet(
            fieldWithPath("data").type(JsonFieldType.STRING).description("감사 멘트"),
        )
}
