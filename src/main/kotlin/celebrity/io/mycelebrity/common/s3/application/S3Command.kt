package celebrity.io.mycelebrity.common.s3.application

import org.springframework.web.multipart.MultipartFile

interface S3Command {

    fun uploadFile(file: MultipartFile): String
}
