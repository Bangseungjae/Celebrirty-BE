package celebrity.io.mycelebrity.common.s3.application.service

import celebrity.io.mycelebrity.common.s3.application.S3Command
import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.IOException
import java.lang.IllegalArgumentException
import java.util.UUID

@Component
class S3Service(
    private val amazonS3: AmazonS3
) : S3Command{

    private val logger: Logger = LoggerFactory.getLogger(S3Service::class.java)

    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    override fun uploadFile(file: MultipartFile): String {
        val filename: String = file.originalFilename
            ?: throw IllegalArgumentException("file의 이름이 null입니다")

        // 파일 형식 구하기
        logger.info("filename=$filename")
        val ext = filename.split(".")[1]
        logger.info("ext: $ext")

        var contentType = getExt(ext)
        val extendedFilename = UUID.randomUUID().toString()

        try {
            val metadata = ObjectMetadata()
            metadata.contentType = contentType

            val bytes = IOUtils.toByteArray(file.inputStream)
            val byteArrayInputStream = ByteArrayInputStream(bytes)
            metadata.contentLength = bytes.size.toLong()
            amazonS3.putObject(
                PutObjectRequest(bucket, extendedFilename, byteArrayInputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.AwsExecRead)
            )
        } catch (e: AmazonServiceException) {
            logger.error("[AWS Ex] : ${e.message}")
        } catch (e: SdkClientException) {
            logger.error("[S3 SDK Ex] : ${e.message}")
        } catch (e: IOException) {
            logger.error("[AWS IO Ex] : ${e.message}")
        }
        return amazonS3.getUrl(bucket, extendedFilename).toString()
    }

    private fun getExt(ext: String): String {
        var contentType: String = ""
        when(ext) {
            "jpeg" -> contentType = "image/jpeg"
            "png" -> contentType = "image/png"
            "txt" -> contentType = "image/plain"
            "csv" -> contentType = "text/csv"
            else -> contentType = ext
        }
        return contentType
    }
}
