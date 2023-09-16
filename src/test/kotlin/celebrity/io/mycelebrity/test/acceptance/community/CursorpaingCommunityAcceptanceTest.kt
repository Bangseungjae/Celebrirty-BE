package celebrity.io.mycelebrity.test.acceptance.community

import celebrity.io.mycelebrity.common.container.AcceptanceTestBase
import celebrity.io.mycelebrity.common.helper.ResponseFieldUtils
import celebrity.io.mycelebrity.common.helper.community.CommentUtils
import celebrity.io.mycelebrity.common.helper.community.CommunityUtils
import io.restassured.RestAssured.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.request.FormParametersSnippet
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document


@DisplayName("커뮤니티 커서 페이징 최신순 인수 테스트")
class CursorPagingCommentAcceptanceTest(
    private val communityUtils: CommunityUtils,
    private val commentUtils: CommentUtils,
) : AcceptanceTestBase(){


    @Test
    fun `커뮤니티 커서 페이징 조회`() {
        val communityId: Long = communityUtils.generateCommunity()
        commentUtils.createComment(communityId)

        given(documentationSpec)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .param("next", 12L)
            .param("size", 4L)
            .filter(
                document(
                    "comment-cursor-paging",
                    formParametersSnippet(),
                    responseFieldsSnippet(),
                    pathParametersSnippet(),
                )
            )
            .`when`()
            .get("/api/comment/paging/{communityId}", communityId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .log().body().log().all()
    }

    private fun pathParametersSnippet(): PathParametersSnippet? = pathParameters(
        parameterWithName("communityId").description("community id값"),
    )

    private fun formParametersSnippet(): FormParametersSnippet? = formParameters(
        parameterWithName("next").description("해당 커뮤니티 게시물의 id값 부터 검색합니다, null값을 넣을시 최신부터 검색").optional(),
        parameterWithName("size").description("null값을 넣을 시 기본 사이즈 10개입니다, 페이지에서 몇개를 검색할지 설정하는 값").optional(),
    )

    private fun responseFieldsSnippet(): ResponseFieldsSnippet =
        ResponseFieldUtils.createResponseFieldSnippet(
            PayloadDocumentation.fieldWithPath("data.values[].id").type(JsonFieldType.NUMBER).description("커뮤니티 id"),
            PayloadDocumentation.fieldWithPath("data.values[].comment").type(JsonFieldType.STRING).description("댓글"),
            PayloadDocumentation.fieldWithPath("data.values[].createdAt").type(JsonFieldType.STRING).description("댓글 날짜"),
            PayloadDocumentation.fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 인덱스가 있는지"),
            PayloadDocumentation.fieldWithPath("data.lastIndex").type(JsonFieldType.NUMBER).description("마지막 인덱스"),
        )
}
