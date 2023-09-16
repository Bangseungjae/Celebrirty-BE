package celebrity.io.mycelebrity.common.container

import celebrity.io.mycelebrity.common.rds.DatabaseCleanup
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@ActiveProfiles("test")
@ExtendWith(RestDocumentationExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AcceptanceTestBase : ContainerTestBase() {

    @Autowired
    lateinit var databaseCleanup: DatabaseCleanup

    @LocalServerPort
    private val port: Int = 8080

    protected var documentationSpec: RequestSpecification = RequestSpecBuilder()
        .setPort(port)
        .setBaseUri(BASE_URL)
        .build()

    @BeforeAll
    fun configureRestAssured() {
        RestAssured.port = port
        RestAssured.baseURI = BASE_URL
    }

    @BeforeEach
    fun setUpRestDocs(restDocumentation: RestDocumentationContextProvider) {
        this.documentationSpec = RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint())
            )
            .build()
    }

    @BeforeEach
    fun cleanUpDatabase() {
        databaseCleanup.execute()
    }
}
private const val BASE_URL: String = "http://localhost"
