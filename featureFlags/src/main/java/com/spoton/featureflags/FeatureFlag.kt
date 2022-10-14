package com.spoton.featureflags

sealed class FeatureFlag<out T : Any>(
    val key: String,
    val type: FeatureFlagType<T>,
    val name: String,
    val defaultDebugValue: T,
    val defaultReleaseValue: T,
) {

    object IsFeature1Enabled: FeatureFlag<Boolean>(
        key = "is_feature_1_enabled",
            type = FeatureFlagType.Logical,
            name = "Is Feature 1 Enabled",
            defaultDebugValue = false,
            defaultReleaseValue = true,
    )

    object ButtonName: FeatureFlag<String>(
        key = "button_name",
        type = FeatureFlagType.Text,
        name = "Button Name",
        defaultDebugValue = "Button Debug",
        defaultReleaseValue = "Button Release",
    )

    object AnalyticSessionFraction: FeatureFlag<Double>(
        key = "analytic_session_fraction",
        type = FeatureFlagType.FloatingPoint,
        name = "Is Feature 1 Enabled",
        defaultDebugValue = 0.0,
        defaultReleaseValue = 0.5,
    )

    object AdsNumber: FeatureFlag<Long>(
        key = "number_of_ads",
        type = FeatureFlagType.Numeric,
        name = "Number of Ads",
        defaultDebugValue = 2,
        defaultReleaseValue = 10,
    )
}
