package celebrity.io.mycelebrity.common.helper

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.ResponseFieldsSnippet

object ResponseFieldUtils {
    fun responseFieldsSnippet(): ResponseFieldsSnippet? = responseFields(
        fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
        fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
        fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
    )

    private fun defaultFieldDescriptors(): MutableList<FieldDescriptor> = mutableListOf(
        fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
        fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
        fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
    )

    private fun MutableList<FieldDescriptor>.addField(fields: Array<out FieldDescriptor>): MutableList<FieldDescriptor> = also {
        fields.forEach { field ->
            it.add(field)
        }
    }

    fun createResponseFieldSnippet(vararg descriptor: FieldDescriptor): ResponseFieldsSnippet =
        defaultFieldDescriptors()
            .addField(descriptor)
            .toResponseFields()

    private fun MutableList<FieldDescriptor>.toResponseFields(): ResponseFieldsSnippet =
        responseFields(this)

}



