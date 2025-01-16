plugins {
    kotlin("jvm") version "1.9.25"
    `maven-publish`
}

group = "org.subproblem"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.jar {
    archiveBaseName.set("common")
}

publishing {
    publications {
        create<MavenPublication>("common") {
            from(components["java"])
        }
    }
}