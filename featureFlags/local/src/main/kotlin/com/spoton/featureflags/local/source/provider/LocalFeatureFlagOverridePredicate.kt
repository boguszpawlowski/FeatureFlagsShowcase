package com.spoton.featureflags.local.source.provider

import android.content.SharedPreferences
import com.spoton.featureflags.source.orchestrator.ShouldUseLocalOverridePredicate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val LOCAL_FEATURE_CONFIG_OVERRIDE_FLAG = "LOCAL_FEATURE_CONFIG_OVERRIDE_FLAG"

internal class LocalFeatureFlagOverridePredicate(
    sharedPreferences: SharedPreferences,
    defaultValue: Boolean = false,
    private val predicateScope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job()),
) : ShouldUseLocalOverridePredicate, SharedPreferences.OnSharedPreferenceChangeListener {

    private var shouldUseLocalFeatureFlagOverride = defaultValue

    override fun test(): Boolean = shouldUseLocalFeatureFlagOverride

    init {
        shouldUseLocalFeatureFlagOverride = sharedPreferences.getBoolean(
            LOCAL_FEATURE_CONFIG_OVERRIDE_FLAG,
            false,
        )
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == LOCAL_FEATURE_CONFIG_OVERRIDE_FLAG) {
            predicateScope.launch {
                shouldUseLocalFeatureFlagOverride =
                    sharedPreferences.getBoolean(
                        LOCAL_FEATURE_CONFIG_OVERRIDE_FLAG,
                        false,
                    )
            }
        }
    }

}
