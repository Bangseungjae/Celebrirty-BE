package celebrity.io.mycelebrity.common.s3

import celebrity.io.mycelebrity.common.s3.application.S3Command
import org.springframework.web.multipart.MultipartFile

class S3FakeService : S3Command{
    override fun uploadFile(file: MultipartFile): String {
        return "aws.ac.kr"
    }
}
