import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
	id("com.android.application") apply false
	id("com.android.library") apply false
	kotlin("android") apply false
	alias(libs.plugins.detekt)
	alias(libs.plugins.versions)
	
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
    }

    dependencies {
        detektPlugins(libs.detekt.formatting)
    }
}

tasks {
    withType<DependencyUpdatesTask>().configureEach {
        rejectVersionIf {
            candidate.version.isStableVersion().not()
        }
    }
}
