plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.github.bagulus.msvcrp4j"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":libraries:microsoft-visual-cpp-redistributable-packages-4j-api:downloader"))
    implementation(project(":libraries:microsoft-visual-cpp-redistributable-packages-4j-api:installer"))
    implementation(project(":libraries:microsoft-visual-cpp-redistributable-packages-4j-api:model"))
}