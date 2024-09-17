import org.asciidoctor.gradle.jvm.AsciidoctorTask

plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "com.jangho"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

val asciidoctorExt = "asciidoctorExt"
configurations.create(asciidoctorExt) {
	extendsFrom(configurations.testImplementation.get())
}

val snippetsDir = file("build/generated-snippets")

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	compileOnly("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks.withType<Test> {
	outputs.dir(snippetsDir)
	useJUnitPlatform()
}

tasks.withType<AsciidoctorTask> {
	inputs.dir(snippetsDir)
	dependsOn(tasks.test)
	configurations(asciidoctorExt)
	baseDirFollowsSourceFile()
}

val copyDocument = tasks.register("copyDocument", Copy::class) {
	dependsOn(tasks.asciidoctor)
	doFirst {
		delete(file("src/main/resources/static/docs"))
	}
	from(file("build/docs/asciidoc"))
	into(file("src/main/resources/static/docs"))
}

tasks.build {
	dependsOn(copyDocument)
}