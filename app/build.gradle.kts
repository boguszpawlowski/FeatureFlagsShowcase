import org.ajoberstar.grgit.Grgit

plugins {
  id(Android.ApplicationPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
  id(Firebase.GoogleServicesPluginId)
  id(Kotlin.KspPluginId) version Kotlin.KspVersion
}

val commitsCount = Grgit.open(mapOf("dir" to rootDir)).log().size

android {
  defaultConfig {
    versionCode = commitsCount
    versionName = App.VersionName
  }
}

dependencies {
  implementation(Kotlin.StdLib)
  implementation(Kotlin.Ksp)

  implementation(Material.Core)

  implementation(AndroidX.AppCompat)
  implementation(AndroidX.ConstraintLayout)
  implementation(AndroidX.ComposeActivity)

  implementation(Compose.Ui)
  implementation(Compose.Foundation)
  implementation(Compose.FoundationLayout)
  implementation(Compose.Material)

  implementation(Coroutines.Core)

  implementation(KotlinXSerialization.Core)
  implementation(KotlinXSerialization.Json)

  implementation(Koin.Core)

  implementation(AndroidX.Activity)
  implementation(AndroidX.Startup)
  implementation(AndroidX.Lifecycle)

  implementation(platform(Firebase.Bom))
  implementation(Firebase.RemoteConfig)

  debugImplementation(Debug.LeakCanary)
  debugImplementation(Hyperion.Core)
  debugImplementation(Hyperion.Plugin)
  kspDebug(Hyperion.autoServiceKsp)
}