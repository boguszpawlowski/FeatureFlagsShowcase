package io.github.boguszpawlowski.featureFlags.provider

import io.github.boguszpawlowski.featureFlags.config.FeatureConfig
import io.github.boguszpawlowski.featureFlags.source.FeatureSource

class FeatureFlagProvider internal constructor(
  private val featureSource: FeatureSource,
) {
  fun get(): FeatureConfig = featureSource.getFeatureConfig()
}
