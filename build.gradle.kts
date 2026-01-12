plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.maur025"
version = "0.0.1-SNAPSHOT"
description = "activity-control"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    /* packages to App */
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // Liquibase
    implementation("org.liquibase:liquibase-core")
    // Hibernate
    implementation("org.hibernate.orm:hibernate-core")
    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // Database
    implementation("org.postgresql:postgresql")
    //security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    // webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    // logger
    implementation("io.github.oshai:kotlin-logging:7.0.7")

    /* packages to test */
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("io.kotest:kotest-assertions-core:6.0.5")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
