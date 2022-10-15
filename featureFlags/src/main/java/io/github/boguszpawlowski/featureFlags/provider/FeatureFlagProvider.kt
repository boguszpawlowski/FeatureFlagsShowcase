package io.github.boguszpawlowski.featureFlags.provider

import io.github.boguszpawlowski.featureFlags.FeatureFlag
import io.github.boguszpawlowski.featureFlags.config.FeatureConfig
import io.github.boguszpawlowski.featureFlags.source.FeatureSource

class FeatureFlagProvider internal constructor(
  private val featureSource: FeatureSource,
) {
    fun get(): FeatureConfig = featureSource.getFeatureConfig()

    fun <T : Any> get(featureFlag: FeatureFlag<T>): T =
        featureSource.getFeatureConfig()[featureFlag]
}
