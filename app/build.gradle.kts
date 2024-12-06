plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.frauddetector"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.frauddetector"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.legacy:legacy-support-v13:1.0.0")

    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))







    implementation("com.google.firebase:firebase-auth")
    implementation ("com.ToxicBakery.viewpager.transforms:view-pager-transforms:2.0.24")


    implementation("com.firebaseui:firebase-ui-database:8.0.2")

    implementation ("com.github.ismaeldivita:chip-navigation-bar:1.4.0")
    implementation("com.google.android.gms:play-services-auth:21.2.0")


    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation("android.arch.persistence.room:runtime:1.1.1")
    annotationProcessor("android.arch.persistence.room:compiler:1.1.1")


    implementation("androidx.compose.material:material-icons-extended-android:1.6.8")


    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.5.0")

    implementation ("com.squareup.picasso:picasso:2.71828")


    implementation ("com.google.code.gson:gson:2.11.0")

    implementation ("com.squareup.retrofit2:retrofit:2.7.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.7.2")
    implementation ("com.squareup.okhttp3:okhttp:3.6.0")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.github.hadiidbouk:ChartProgressBar-Android:2.0.6")

    implementation("com.github.mancj:SlimChart:0.1.2")
    implementation ("androidx.work:work-runtime:2.8.1")
}