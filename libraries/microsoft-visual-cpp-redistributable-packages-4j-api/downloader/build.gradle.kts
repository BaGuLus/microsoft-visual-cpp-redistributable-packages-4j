plugins {
    id("java")
}

group = "com.github.bagulus.msvcrp4j"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":libraries:microsoft-visual-cpp-redistributable-packages-4j-api:model"))
}

tasks.test {
    useJUnitPlatform()
}