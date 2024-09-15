plugins {
    java
    kotlin("jvm") version "1.9.21"
    id("maven-publish")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.nesterenko1pavel"
            artifactId = "yandex-messenger-bot"
            version = "1.0.13"

            from(components["java"])
        }
    }
}