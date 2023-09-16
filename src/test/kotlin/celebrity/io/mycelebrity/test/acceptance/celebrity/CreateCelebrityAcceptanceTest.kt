package celebrity.io.mycelebrity.test.acceptance.celebrity

import celebrity.io.mycelebrity.celebrity.application.dto.RequestCelebrity
import celebrity.io.mycelebrity.common.container.AcceptanceTestBase
import celebrity.io.mycelebrity.common.helper.ResponseFieldUtils
import celebrity.io.mycelebrity.common.helper.celebrity.CelebrityGroupHelper
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.*
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestPartsSnippet
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document

@DisplayName("셀럽 생성 인수 테스트")
class CreateCelebrityAcceptanceTest(
    private val celebrityGroupHelper: CelebrityGroupHelper,
) : AcceptanceTestBase(){

    @Test
    fun `Insert Celebrity Then Return OK`() {

        val groupId = celebrityGroupHelper.createGroup()
        val fileByte = "Hello, World!".encodeToByteArray()
        given(documentationSpec)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("file", "file.png", fileByte, MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("request", getJsonRequest(), MediaType.APPLICATION_JSON_VALUE)
            .filter(
                document(
                    "insert-celebrity",
                    requestPartFieldsSnippet(),
                    responseFieldsSnippet(),
                    pathParametersSnippet(),
                    getRequestPartsSnippet(),
                )
            )
            .`when`()
            .post("/api/celebrity/{groupId}", groupId)
            .then().statusCode(HttpStatus.OK.value())
            .log().body().log().all()
    }

    private fun requestPartFieldsSnippet(): RequestPartFieldsSnippet? =
        requestPartFields(
            "request",
            fieldWithPath("celebrityName").type(JsonFieldType.STRING).description("연예인 이름"),
            fieldWithPath("celebrityDescription").type(JsonFieldType.STRING).description("연예인 설명"),
            fieldWithPath("communityDescription").type(JsonFieldType.STRING).description("커뮤니티 설명"),
        )

    private fun getRequestPartsSnippet(): RequestPartsSnippet? {
        return RequestDocumentation.requestParts(
            RequestDocumentation.partWithName("request").description("셀럽 만드는데 필요한 JSON 데이터"),
            RequestDocumentation.partWithName("file").description("커뮤니티에 넣을 사진")
        )
    }

    private fun responseFieldsSnippet(): ResponseFieldsSnippet =
        ResponseFieldUtils.createResponseFieldSnippet(
            fieldWithPath("data").type(JsonFieldType.NUMBER).description("셀럽 id값"),
        )

    private fun pathParametersSnippet(): PathParametersSnippet? = RequestDocumentation.pathParameters(
        RequestDocumentation.parameterWithName("groupId").description("group 인덱스 값")
    )

    private fun getJsonRequest(): RequestCelebrity {
        return RequestCelebrity(
            celebrityName = "Hani",
            celebrityDescription = "beautiful",
            communityDescription = "good"
        )
    }
}
