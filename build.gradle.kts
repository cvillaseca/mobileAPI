import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.7.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"

	id("io.gitlab.arturbosch.detekt").version("1.8.0")
}

group = "com.cvillaseca"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.security.oauth:spring-security-oauth2:2.4.1.RELEASE")

	runtimeOnly("com.h2database:h2")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(module = "mockito-core")
	}
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("com.ninja-squad:springmockk:2.0.1")

	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.8.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

val detektAll by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
	description = "Runs over whole code base without the starting overhead for each module."
	autoCorrect = true
	parallel = true
	setSource(files(projectDir))
	include("**/*.kt")
	include("**/*.kts")
	exclude("**/resources/**")
	exclude("**/build/**")
	exclude("**/buildSrc/**")
	exclude("**/test/**/*.kt")
	config.setFrom(files("$rootDir/detekt/detekt.yml"))
	baseline.set(file("$rootDir/detekt/baseline.xml"))
	reports {
		xml.enabled = false
		html.enabled = false
		txt.enabled = false
	}
}

val detektProjectBaseline by tasks.registering(io.gitlab.arturbosch.detekt.DetektCreateBaselineTask::class) {
	description = "Overrides current baseline."
	ignoreFailures.set(true)
	parallel.set(true)
	setSource(files(rootDir))
	config.setFrom(files("$rootDir/detekt/detekt.yml"))
	baseline.set(file("$rootDir/detekt/baseline.xml"))
	include("**/*.kt")
	include("**/*.kts")
	exclude("**/resources/**")
	exclude("**/build/**")
	exclude("**/buildSrc/**")
	exclude("**/test/**/*.kt")
}
