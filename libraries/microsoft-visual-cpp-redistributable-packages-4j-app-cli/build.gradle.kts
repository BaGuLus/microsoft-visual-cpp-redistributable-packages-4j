plugins {
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.github.bagulus.msvcrp4j"
version = "1.0-SNAPSHOT"

application {
    mainClass = "com.github.bagulus.msvcrp4j.app.cli.MicrosoftVisualCppRedistributableDeploymentApp"
}

dependencies {
    implementation(libs.semver4j)
    implementation(project(":libraries:microsoft-visual-cpp-redistributable-packages-4j-api:downloader"))
    implementation(project(":libraries:microsoft-visual-cpp-redistributable-packages-4j-api:installer"))
    implementation(project(":libraries:microsoft-visual-cpp-redistributable-packages-4j-api:model"))
}

tasks {
    test {
        useJUnitPlatform()
    }
}
