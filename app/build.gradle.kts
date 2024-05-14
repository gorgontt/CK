import org.gradle.internal.impldep.bsh.commands.dir


plugins {
    alias(libs.plugins.androidApplication)

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.shop"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shop"
        minSdk = 29
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")

    implementation ("com.google.firebase:firebase-database:21.0.0")
    implementation ("com.google.firebase:firebase-storage:21.0.0")

    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.github.rey5137:material:1.3.1")

    implementation ("androidx.navigation:navigation-fragment:2.7.7")
    implementation ("androidx.navigation:navigation-ui:2.7.7")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

   // implementation ("io.paperdb:paperdb:2.7.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
   // implementation ("com.theartofdev.edmodo:android-image-cropper:2.8.0")
    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    //implementation ("com.firebaseui:firebase-ui-storage:8.0.2")

    implementation (libs.pilgr.paperdb)
    implementation(libs.firebase.auth)


   // implementation (libs.firebase.core)
    implementation ("com.google.firebase:firebase-core:17.5.1")
    //implementation (libs.firebase.ads)
    implementation ("com.google.firebase:firebase-ads:10.2.1")
    implementation (libs.paging.runtime)
    //implementation (libs.android.image.cropper)


  /*


    implementation ("com.google.firebase:firebase-analytics:17.2.2")


    implementation ("com.firebaseui:firebase-ui-database:6.1.0")






   */
}



