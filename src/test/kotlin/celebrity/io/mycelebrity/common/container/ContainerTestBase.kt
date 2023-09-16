package celebrity.io.mycelebrity.common.container

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@ContextConfiguration(initializers = [ContainerTestBase.DatasourceInitializer::class])
abstract class ContainerTestBase {
    class DatasourceInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        private val MYSQL_IMAGE = "mysql:latest"

        @Container
        private val database = MySQLContainer(MYSQL_IMAGE)

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            database.start()
            TestPropertyValues.of(
                "spring.datasource.url=${database.jdbcUrl}",
                "spring.datasource.username=${database.username}",
                "spring.datasource.password=${database.password}"
            ).applyTo(applicationContext.environment)
        }
    }
}
