import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

repositories {
	google()
	mavenCentral()
	gradlePluginPortal()
}

dependencies {
    implementation(libs.kgp)
    implementation(libs.agp)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}