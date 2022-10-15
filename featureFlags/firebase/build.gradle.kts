plugins {
  id(Android.LibraryPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
}

dependencies {
  implementation(project(autoModules.featureFlags))

  implementation(Kotlin.StdLib)

  implementation(platform(Firebase.Bom))
  implementation(Firebase.RemoteConfig)

  implementation(Koin.Core)
}
