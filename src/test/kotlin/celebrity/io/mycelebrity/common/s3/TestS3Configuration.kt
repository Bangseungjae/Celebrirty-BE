package celebrity.io.mycelebrity.common.s3

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class TestS3Configuration {

    @Primary
    @Bean
    fun s3FakeService(): S3FakeService = S3FakeService()
}
