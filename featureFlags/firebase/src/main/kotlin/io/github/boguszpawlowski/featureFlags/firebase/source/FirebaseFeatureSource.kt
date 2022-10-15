package io.github.boguszpawlowski.featureFlags.firebase.source

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.github.boguszpawlowski.featureFlags.config.FeatureConfig
import io.github.boguszpawlowski.featureFlags.source.FeatureSource

internal class FirebaseFeatureSource(
  private val firebaseFeatureMapper: FirebaseFeatureMapper = FirebaseFeatureMapper(),
  private val remoteConfig: FirebaseRemoteConfig,
  private val isDebug: Boolean,
) : FeatureSource {
  private var featureConfig: FeatureConfig = firebaseFeatureMapper.map(
    remoteValues = remoteConfig.all,
    isDebug = isDebug,
  )

  init {
    fetchRemoteConfig()
  }

  override fun getFeatureConfig(): FeatureConfig =
    featureConfig.also {
      fetchRemoteConfig()
    }

  private fun fetchRemoteConfig() {
    remoteConfig.fetchAndActivate().addOnSuccessListener { isFetchedFromFirebase ->
      if (isFetchedFromFirebase.not()) {
        return@addOnSuccessListener
      }

      val newConfig = firebaseFeatureMapper.map(
        remoteValues = remoteConfig.all,
        isDebug = isDebug,
      )

      if (featureConfig != newConfig) {
        featureConfig = newConfig
      }
    }
  }
}
