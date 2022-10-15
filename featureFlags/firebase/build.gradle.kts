plugins {
    id(Android.LibraryPluginId)
    id("common-android-plugin")
}

dependencies {
  implementation(project(":featureFlags"))

  implementation(Kotlin.StdLib)

  implementation(platform(Firebase.Bom))
  implementation(Firebase.RemoteConfig)

  implementation(Koin.Core)
}
