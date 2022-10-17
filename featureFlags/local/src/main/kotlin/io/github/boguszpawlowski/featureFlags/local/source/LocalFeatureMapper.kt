package io.github.boguszpawlowski.featureFlags.local.source

import io.github.boguszpawlowski.featureFlags.Feature
import io.github.boguszpawlowski.featureFlags.FeatureFlagType
import io.github.boguszpawlowski.featureFlags.config.FeatureConfig

internal class LocalFeatureMapper(
  private val features: Array<Feature> = Feature.values(),
) {

  fun map(localFeatureFlagsWithValues: Map<String, *>): FeatureConfig =
    features.associate { feature ->
      with(feature) {
        val rawLocalValue = localFeatureFlagsWithValues[featureFlag.key]
        val remoteValue = when (featureFlag.type) {
          FeatureFlagType.Logical -> rawLocalValue as? Boolean
          FeatureFlagType.Numeric -> rawLocalValue as? Long
          FeatureFlagType.FloatingPoint -> rawLocalValue as? Float
          FeatureFlagType.Text -> rawLocalValue as? String
        }

        featureFlag to (remoteValue ?: featureFlag.defaultDebugValue)
      }
    }.let(::FeatureConfig)
}
