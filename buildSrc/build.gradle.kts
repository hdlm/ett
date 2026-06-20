plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

tasks.named("validatePlugins") {
    enabled = false
}

