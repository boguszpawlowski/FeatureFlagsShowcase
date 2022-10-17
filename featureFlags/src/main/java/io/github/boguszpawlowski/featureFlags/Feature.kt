package io.github.boguszpawlowski.featureFlags


enum class Feature(val featureFlag: FeatureFlag<*>) {
  IS_FEATURE_1_ENABLED(FeatureFlag.IsFeature1Enabled),
  BUTTON_NAME(FeatureFlag.ButtonName),
  ANALYTIC_SESSION_FRACTION(FeatureFlag.AnalyticSessionFraction),
  ADS_NUMBER(FeatureFlag.AdsNumber),
}
