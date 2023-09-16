import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
	id("io.kotest.multiplatform") version "5.0.2"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
	kotlin("kapt") version "1.7.10"

	idea
}

group = "celebrity.io"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("jakarta.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
	annotation("jakarta.persistence.Embeddable")
}

val snippetsDir = file("$buildDir/generated-snippets")
val asciidoctorExt: Configuration by configurations.creating

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// MySQL
	runtimeOnly("com.mysql:mysql-connector-j")

	// JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	kapt ("com.querydsl:querydsl-apt:5.0.0:jakarta")
	kapt ("jakarta.annotation:jakarta.annotation-api")
	kapt ("jakarta.persistence:jakarta.persistence-api")
	implementation("org.springframework.data:spring-data-envers")
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")


	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.6.RELEASE")
	testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor:2.0.6.RELEASE")
	testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
	testImplementation("io.kotest:kotest-assertions-core:5.5.5")
	testImplementation("io.rest-assured:rest-assured")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mysql")
	testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
	testImplementation("io.kotest:kotest-assertions-core:5.5.5")
	testImplementation("org.mockito:mockito-core:4.8.1")

	// AWS S3
	implementation("io.awspring.cloud:spring-cloud-starter-aws-ses:2.4.4")
	implementation("commons-fileupload:commons-fileupload:1.5")
	implementation("commons-io:commons-io:2.11.0")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Google
	implementation("com.google.guava:guava:31.1-jre")
	testImplementation("com.google.code.gson:gson:2.10.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	outputs.dir(snippetsDir)
}

tasks.asciidoctor {
	inputs.dir(snippetsDir)
	configurations("asciidoctorExt")
	dependsOn(tasks.test)
	baseDirFollowsSourceDir()

	doFirst {
		delete {
			file("src/main/resources/static/docs")
		}
	}
}

tasks.register("copyHTML", Copy::class) {
	dependsOn(tasks.asciidoctor)
	from(file("build/docs/asciidoc"))
	into(file("src/main/resources/static/docs"))
}

tasks.build {
	dependsOn(tasks.getByName("copyHTML"))
}

tasks.bootJar {
	dependsOn(tasks.getByName("copyHTML"))
}

// ✅ QClass를 Intellij가 사용할 수 있도록 경로에 추가합니다
var querydslDir = "$buildDir/generated/source/kapt/main"
idea {
	module {
		val kaptMain = file(querydslDir)
		sourceDirs.add(kaptMain)
		generatedSourceDirs.add(kaptMain)
	}
}
