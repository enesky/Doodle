plugins {
    // Convention Plugins
    alias(libs.plugins.doodle.android.application)
    alias(libs.plugins.doodle.android.application.compose)
}

android.namespace = "dev.enesky.core.common"

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}