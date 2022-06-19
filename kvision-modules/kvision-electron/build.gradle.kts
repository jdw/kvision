plugins {
    kotlin("js")
    id("maven-publish")
    id("signing")
    id("de.marcphilipp.nexus-publish")
    id("org.jetbrains.dokka")
}

// Versions
val nodeJsVersion: String by project

kotlin {
    js {
        compilations.all {
            kotlinOptions {
                moduleKind = "umd"
            }
        }
        nodejs {
            testTask {
                useKarma()
            }
        }
    }
}

dependencies {
    api(rootProject)
    api("org.jetbrains.kotlinx:kotlinx-nodejs:$nodeJsVersion")
    implementation(npm("electron", "^17.0.0"))
    implementation(npm("@electron/remote", "^2.0.4"))
    testImplementation(kotlin("test-js"))
}

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn("irGenerateExternalsIntegrated")
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.main.get().kotlin)
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from("$buildDir/dokka/html")
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            if (!hasProperty("SNAPSHOT")) artifact(tasks["javadocJar"])
        }
    }
}

setupSigning()
setupPublication()
setupDokka()
