package com.spoton.featureflags.local.source

import com.spoton.featureflags.Feature
import com.spoton.featureflags.config.FeatureConfig

internal class LocalDefaultFeatureConfigProvider(
    private val features: Array<Feature> = Feature.values(),
) {

    fun provide(): FeatureConfig =
        FeatureConfig(
            featureFlags = features.associate {
                it.featureFlag to it.featureFlag.defaultDebugValue
            }
        )
}
