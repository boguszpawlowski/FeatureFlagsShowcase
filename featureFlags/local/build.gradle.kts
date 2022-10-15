plugins {
  id(Android.LibraryPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
}

dependencies {
  implementation(project(autoModules.featureFlags))

  implementation(Koin.Core)
}
