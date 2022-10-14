package com.spoton.featureflags.config

import com.spoton.featureflags.FeatureFlag

@Suppress("UNCHECKED_CAST")
class FeatureConfig(
    val featureFlags: Map<FeatureFlag<*>, *>,
) {
    constructor(
        vararg featureFlags: Pair<FeatureFlag<*>, *>,
    ) : this(featureFlags.toMap())

    operator fun <T : Any> get(featureFlag: FeatureFlag<T>): T = featureFlags[featureFlag] as T

}
