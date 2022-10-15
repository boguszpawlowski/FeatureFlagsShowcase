package io.github.boguszpawlowski.featureFlags.firebase.source

import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import io.github.boguszpawlowski.featureFlags.Feature
import io.github.boguszpawlowski.featureFlags.FeatureFlagType
import io.github.boguszpawlowski.featureFlags.config.FeatureConfig

internal class FirebaseFeatureMapper(
  private val features: Array<Feature> = Feature.values(),
) {
  fun map(
    remoteValues: Map<String, FirebaseRemoteConfigValue>,
    isDebug: Boolean,
  ): FeatureConfig {
    val featureFlags = features.associate {
      val featureFlag = it.featureFlag
      val defaultValue = when (isDebug) {
        true -> featureFlag.defaultDebugValue
        false -> featureFlag.defaultReleaseValue
      }

      val rawRemoteValue = remoteValues[featureFlag.key]
      val remoteValue = when (featureFlag.type) {
        FeatureFlagType.Logical -> rawRemoteValue?.asBoolean()
        FeatureFlagType.Numeric -> rawRemoteValue?.asLong()
        FeatureFlagType.FloatingPoint -> rawRemoteValue?.asDouble()?.toFloat()
        FeatureFlagType.Text -> rawRemoteValue?.asString()
      }

      featureFlag to (remoteValue ?: defaultValue)
    }

    return FeatureConfig(featureFlags)
  }
}
