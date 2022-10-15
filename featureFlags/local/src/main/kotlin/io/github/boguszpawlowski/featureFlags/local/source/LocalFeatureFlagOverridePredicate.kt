package io.github.boguszpawlowski.featureFlags.local.source

import android.content.SharedPreferences
import io.github.boguszpawlowski.featureFlags.source.orchestrator.ShouldUseLocalOverridePredicate

const val LocalFeatureConfigOverrideFlag = "LOCAL_FEATURE_CONFIG_OVERRIDE_FLAG"

internal class LocalFeatureFlagOverridePredicate(
  private val sharedPreferences: SharedPreferences,
) : ShouldUseLocalOverridePredicate {

  override fun test(): Boolean =
    sharedPreferences.getBoolean(LocalFeatureConfigOverrideFlag, false,)
}
