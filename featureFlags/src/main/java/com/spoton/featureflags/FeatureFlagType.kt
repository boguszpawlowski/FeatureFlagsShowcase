package com.spoton.featureflags

sealed interface FeatureFlagType<out T : Any> {
    object Logical : FeatureFlagType<Boolean>
    object Numeric : FeatureFlagType<Long>
    object FloatingPoint : FeatureFlagType<Double>
    object Text : FeatureFlagType<String>
}
