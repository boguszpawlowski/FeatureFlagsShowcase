package com.spoton.featureFlags.firebase.source

import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.spoton.featureflags.Feature
import com.spoton.featureflags.FeatureFlagType
import com.spoton.featureflags.config.FeatureConfig

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
                FeatureFlagType.FloatingPoint -> rawRemoteValue?.asDouble()
                FeatureFlagType.Text -> rawRemoteValue?.asString()
            }

            featureFlag to (remoteValue ?: defaultValue)
        }

        return FeatureConfig(featureFlags)
    }
}
