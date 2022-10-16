import org.ajoberstar.grgit.Grgit

plugins {
  id(Android.ApplicationPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
  id(Firebase.GoogleServicesPluginId)
  id(Kotlin.KspPluginId) version Kotlin.KspVersion
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 0
val currentVersionCode = versionMajor * 1000 + versionMinor * 10 + versionPatch

android {
  defaultConfig {
    versionCode = currentVersionCode
    versionName = "$versionMajor.$versionMinor.$versionPatch"
  }

  buildFeatures.compose = true

  composeOptions {
    kotlinCompilerExtensionVersion = Compose.CompilerVersion
  }
}

dependencies {
  implementation(project(autoModules.featureFlags))
  implementation(project(autoModules.featureFlags.firebase))
  implementation(project(autoModules.featureFlags.local))

  implementation(Kotlin.StdLib)
  implementation(Kotlin.Ksp)

  implementation(Material.Core)

  implementation(AndroidX.AppCompat)
  implementation(AndroidX.ConstraintLayout)
  implementation(AndroidX.ComposeActivity)

  implementation(Compose.Ui)
  implementation(Compose.UiTooling)
  implementation(Compose.Foundation)
  implementation(Compose.FoundationLayout)
  implementation(Compose.Material)

  implementation(Coroutines.Core)

  implementation(KotlinXSerialization.Core)
  implementation(KotlinXSerialization.Json)

  implementation(Koin.Core)
  implementation(Koin.Android)
  implementation(Koin.Compose)

  implementation(Timber.Core)

  implementation(AndroidX.Activity)
  implementation(AndroidX.Startup)
  implementation(AndroidX.Lifecycle)

  implementation(platform(Firebase.Bom))
  implementation(Firebase.RemoteConfig)
  implementation(Firebase.Analytics)

  debugImplementation(Hyperion.Core)
  debugImplementation(Hyperion.Plugin)
  kspDebug(Hyperion.autoServiceKsp)
}
