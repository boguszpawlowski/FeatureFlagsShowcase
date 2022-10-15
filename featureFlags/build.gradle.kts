plugins {
  kotlin(Kotlin.JvmPluginId)
}

repositories {
  mavenCentral()
  google()
}

dependencies {
  implementation(Kotlin.StdLib)
  implementation(Koin.Core)
}
