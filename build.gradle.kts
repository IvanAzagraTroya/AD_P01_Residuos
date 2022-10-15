import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"

    id("org.jetbrains.kotlinx.dataframe") version "0.8.1"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    implementation("org.jetbrains.kotlinx:dataframe:0.8.1")
    // implementation("org.jetbrains.kotlinx:dataframe-core:0.8.1")

    // Instalamos la librería Krangl
    //implementation("com.github.holgerbrandl:krangl:0.18.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}