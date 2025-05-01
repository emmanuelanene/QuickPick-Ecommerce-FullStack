plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.backend"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// STARTERS
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("io.github.cdimascio:dotenv-java:3.2.0")

	// JWTs
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
	implementation(("io.jsonwebtoken:jjwt-jackson:0.12.6"))

	// OPEN API for Swagger Docs
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")

	// DEV TOOLS
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")

	// DATABSES
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("mysql:mysql-connector-java:8.0.33")

	// TESTING
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// PAYMENT
	implementation("com.stripe:stripe-java:29.0.0")

	implementation("org.springframework.boot:spring-boot-starter-security:3.4.2")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.4.4")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
