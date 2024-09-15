plugins {
    kotlin("jvm") version "1.9.21"
    id("maven-publish")
}

group = "honey.bot.api"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.21")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}