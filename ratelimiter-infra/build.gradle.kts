plugins {
    kotlin("jvm")
    id("io.spring.dependency-management")
    id("org.springframework.boot") version "3.3.1" apply false
}

dependencies {
    implementation(project(":ratelimiter-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.5.4")
    implementation("io.lettuce:lettuce-core:6.6.0.RELEASE")}