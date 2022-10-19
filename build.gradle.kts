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

    implementation("org.jvnet.jaxb2.maven2:maven-jaxb2-plugin:0.15.1")

    // Dataframes
    implementation("org.jetbrains.kotlinx:dataframe:0.8.1")
    // LetsPlot
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin:3.2.0")
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.3.0")

    //gson
    implementation("com.google.code.gson:gson:2.9.1")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

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