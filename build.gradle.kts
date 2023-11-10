import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.17"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.9.20"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
}

group = "com.app"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_18

noArg {
    annotation("jakarta.persistence.Entity")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:2.2.1.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.junit.jupiter:junit-jupiter:5.9.0")
    implementation("org.springdoc:springdoc-openapi-data-rest:1.7.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.7.0")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.7.0")
    implementation("io.arrow-kt:arrow-core:1.2.1")
    runtimeOnly("com.h2database:h2")
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest:kotest-property:5.8.0")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.4.0")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "18"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

