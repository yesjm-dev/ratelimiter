plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.23"
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":ratelimiter-application"))
    implementation(project(":ratelimiter-domain"))
    implementation(project(":ratelimiter-infra"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers:1.20.1")
    testImplementation("org.testcontainers:junit-jupiter:1.20.1")
}

tasks.test {
    useJUnitPlatform()
}