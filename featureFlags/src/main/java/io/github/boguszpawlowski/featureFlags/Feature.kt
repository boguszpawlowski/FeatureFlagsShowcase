package io.github.boguszpawlowski.featureFlags

import io.github.boguszpawlowski.featureFlags.FeatureFlag.AdsNumber as AdsNumberFlag
import io.github.boguszpawlowski.featureFlags.FeatureFlag.AnalyticSessionFraction as AnalyticSessionFractionFlag
import io.github.boguszpawlowski.featureFlags.FeatureFlag.ButtonName as ButtonNameFlag
import io.github.boguszpawlowski.featureFlags.FeatureFlag.IsFeature1Enabled as IsFeature1Enabled1Flag

enum class Feature(val featureFlag: FeatureFlag<*>) {
    IsFeature1Enabled(IsFeature1Enabled1Flag),
    ButtonName(ButtonNameFlag),
    AnalyticSessionFraction(AnalyticSessionFractionFlag),
    AdsNumber(AdsNumberFlag),
}
