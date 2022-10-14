package com.spoton.featureflags.provider

import com.spoton.featureflags.FeatureFlag
import com.spoton.featureflags.config.FeatureConfig
import com.spoton.featureflags.source.FeatureSource

class FeatureFlagProvider internal constructor(
    private val featureSource: FeatureSource,
) {
    fun get(): FeatureConfig = featureSource.getFeatureConfig()

    fun <T : Any> get(featureFlag: FeatureFlag<T>): T =
        featureSource.getFeatureConfig()[featureFlag]
}
