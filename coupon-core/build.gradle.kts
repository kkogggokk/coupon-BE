val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.github.ben-manes.caffeine:caffeine")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // v1.2.0 coupon-api(Redis Lock)
    implementation("org.redisson:redisson-spring-boot-starter:3.16.4")

    // 아래 2개 어디서 추가된 거지? 확인 필요 CHECK, 프로젝트 하면서 생긴걸수도 ? 우선 생략해보고
    // Spring Web 의존성 추가
    implementation ("org.springframework.boot:spring-boot-starter-web")
    // Spring Data JPA 의존성 추가: JPA를 사용하기 위한 의존성
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
}

tasks.withType<Test> {
    useJUnitPlatform()
}