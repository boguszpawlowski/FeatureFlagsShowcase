package com.spoton.featureflags

import com.spoton.featureflags.FeatureFlag.AdsNumber as AdsNumberFlag
import com.spoton.featureflags.FeatureFlag.AnalyticSessionFraction as AnalyticSessionFractionFlag
import com.spoton.featureflags.FeatureFlag.ButtonName as ButtonNameFlag
import com.spoton.featureflags.FeatureFlag.IsFeature1Enabled as IsFeature1Enabled1Flag

enum class Feature(val featureFlag: FeatureFlag<*>) {
    IsFeature1Enabled(IsFeature1Enabled1Flag),
    ButtonName(ButtonNameFlag),
    AnalyticSessionFraction(AnalyticSessionFractionFlag),
    AdsNumber(AdsNumberFlag),
}
