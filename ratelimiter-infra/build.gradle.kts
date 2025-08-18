plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":ratelimiter-domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("io.lettuce.core:lettuce-core:6.2.8")
}