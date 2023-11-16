plugins {
    id("java")
    application
}

group = "com.github.bagulus.msvcrj"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.robtimus:windows-registry:1.0.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("com/github/bagulus/msvcrj/app/MicrosoftVisualCppRedistributableInstallationApp")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "com/github/bagulus/msvcrj/app/MicrosoftVisualCppRedistributableInstallationApp"
    val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}