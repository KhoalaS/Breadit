plugins {
    id("kotlin-kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.khoalas.breadit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.khoalas.breadit"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Appyx
    implementation(libs.bumble.appyx)

    // Navigation
    implementation(libs.androidx.navigation)

    // Apollo
    implementation(libs.apollo.runtime)

    // Retrofit
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.converter.gson)
    implementation(libs.squareup.okhttp3.logging.interceptor)

    // DI
    kapt(libs.google.dagger.hilt.compiler)
    implementation(libs.google.dagger.hilt)

    // Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.hilt.navigation.compose)

    // Datastore
    implementation(libs.androidx.datastore)

    implementation(libs.coil3.compose)
    implementation(libs.coil3.network.okhttp)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

apollo {
    service("service") {
        packageName.set("com.khoalas.breadit.apollo")
        schemaFile.set(file("src/main/graphql/com/khoalas/breadit/schema.graphqls"))
        introspection {
            endpointUrl.set("https://gql-fed.reddit.com/")
        }
        operationManifestFormat.set("persistedQueryManifest")
    }
}