plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.23"
    id("io.spring.dependency-management")
    id("org.springframework.boot") version "3.3.1" apply false
}

dependencies {
    implementation(project(":ratelimiter-domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.5.4")
    implementation("io.lettuce:lettuce-core:6.6.0.RELEASE")

    testImplementation("org.mockito:mockito-core:5.19.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}

tasks.test {
    useJUnitPlatform()
}