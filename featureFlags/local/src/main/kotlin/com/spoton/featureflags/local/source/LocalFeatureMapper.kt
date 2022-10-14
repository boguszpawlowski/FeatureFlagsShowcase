package com.spoton.featureflags.local.source

import com.spoton.featureflags.Feature
import com.spoton.featureflags.FeatureFlagType
import com.spoton.featureflags.config.FeatureConfig

internal class LocalFeatureMapper(
    private val features: Array<Feature> = Feature.values(),
) {

    fun map(localFeatureFlagsWithValues: Collection<LocalFeatureFlagWithValue>): FeatureConfig =
        features.associate { feature ->
            with(feature) {
                val rawLocalValue =
                    localFeatureFlagsWithValues.find { localFlag -> localFlag.featureFlagId == featureFlag.key }?.value
                val remoteValue = when (featureFlag.type) {
                    FeatureFlagType.Logical -> rawLocalValue as? Boolean
                    FeatureFlagType.Numeric -> (rawLocalValue as? String)?.toLong()
                    FeatureFlagType.FloatingPoint -> rawLocalValue as? Double
                    FeatureFlagType.Text -> rawLocalValue as? String
                }

                featureFlag to (remoteValue ?: featureFlag.defaultDebugValue)
            }
        }.let(::FeatureConfig)
}

internal interface LocalFeatureFlagWithValue {
    val featureFlagId: String
    val value: Any
}
