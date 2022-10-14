plugins {
    id(Android.LibraryPluginId)
    id("common-android-plugin")
}

dependencies {
    implementation(project(autoModules.featureFlags))

    implementation(Coroutines.Core)
    implementation(Timber.Core)
}
