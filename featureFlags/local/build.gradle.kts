plugins {
    id(Android.LibraryPluginId)
    id("common-android-plugin")
}

dependencies {
    implementation(project(":featureFlags"))

    implementation(Koin.Core)
}
