plugins {
    id("java")
}

group = "com.github.bagulus.msvcrp4j"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.semver4j)
    implementation(libs.windows.registry)
}

tasks.test {
    useJUnitPlatform()
}