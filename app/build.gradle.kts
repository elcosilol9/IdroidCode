plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("parcelize")
}

android {
    compileSdk = libs.versions.compile.sdk.version.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.version.get().toInt()
        targetSdk = libs.versions.target.sdk.version.get().toInt()
        namespace = AppCoordinates.PACKAGE_NAME

        applicationId = AppCoordinates.PACKAGE_NAME
        versionCode = AppCoordinates.VERSION_CODE
        versionName = AppCoordinates.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        vectorDrawables.useSupportLibrary = true
    }
    buildFeatures {
		buildConfig = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        //isCoreLibraryDesugaringEnabled = true 
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        named("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
        }
    }

    lint {
        warningsAsErrors = false
        abortOnError = false
        disable.addAll(arrayOf("VectorPath", "NestedWeights", "ContentDescription", "SmallSp"))

    }
    
    kapt {
    	useBuildCache = true
    	correctErrorTypes = true
	}

    packagingOptions {
        resources.excludes.add("META-INF/*")
        jniLibs.useLegacyPackaging = false
    }
}

dependencies {
    implementation(projects.common)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.espresso.core)
}
