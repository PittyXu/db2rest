plugins {
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.github"
version = "0.3.1-SNAPSHOT"
description = "db2rest"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.processResources {
    filesMatching("application.yml") {
        filter { line ->
            line.replace("@project.version@", project.version.toString())
                .replace("@project.name@", project.name)
        }
    }
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.16.1")
    implementation("commons-io:commons-io:2.15.1")
    implementation("io.github.nstdio:rsql-parser:2.2.1")
    implementation("io.hypersistence:tsid:1.1.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.checkerframework:checker-qual:3.42.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.3.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    implementation("com.oracle.database.jdbc:ojdbc11:21.9.0.0")
    implementation("org.postgresql:postgresql:42.6.2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.3.3")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:3.0.1")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mariadb")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:oracle-xe")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("io.hosuaby:inject-resources-junit-jupiter:0.3.3")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
