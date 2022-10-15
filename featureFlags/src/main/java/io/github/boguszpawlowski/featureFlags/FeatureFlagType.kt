package io.github.boguszpawlowski.featureFlags

sealed interface FeatureFlagType<out T : Any> {
    object Logical : FeatureFlagType<Boolean>
    object Numeric : FeatureFlagType<Long>
    object FloatingPoint : FeatureFlagType<Double>
    object Text : FeatureFlagType<String>
}
