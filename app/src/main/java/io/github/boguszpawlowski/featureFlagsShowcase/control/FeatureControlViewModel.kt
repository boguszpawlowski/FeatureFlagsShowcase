package io.github.boguszpawlowski.featureFlagsShowcase.control

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import io.github.boguszpawlowski.featureFlags.FeatureFlag
import io.github.boguszpawlowski.featureFlags.FeatureFlagType
import io.github.boguszpawlowski.featureFlags.config.FeatureConfig
import io.github.boguszpawlowski.featureFlags.local.source.LocalFeatureConfigOverrideFlag
import io.github.boguszpawlowski.featureFlags.provider.FeatureFlagProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeatureControlViewModel(
  private val featureFlagProvider: FeatureFlagProvider,
  private val saveFeatureFlagValue: SaveFeatureFlagValue,
  private val saveLocalConfigOverride: SaveLocalConfigOverride,
  private val loadLocalConfigOverride: LoadLocalConfigOverride,
) : ViewModel() {

  private val _viewState = MutableStateFlow(ViewState())
  val viewState: StateFlow<ViewState> = _viewState

  init {
    fetchFeatureConfig()
    fetchLocalConfigOverride()
  }

  fun <T : Any> onValueChanged(featureFlag: FeatureFlag<T>, newValue: T) {
    saveFeatureFlagValue(featureFlag, newValue)
    fetchFeatureConfig()
  }

  fun onLocalOverrideChanged(newValue: Boolean) {
    saveLocalConfigOverride(newValue)
    fetchLocalConfigOverride()
    fetchFeatureConfig()
  }

  private fun fetchFeatureConfig() {
    _viewState.value = _viewState.value.copy(
      featureConfig = featureFlagProvider.get(),
    )
  }

  private fun fetchLocalConfigOverride() {
    _viewState.value = _viewState.value.copy(
      isUsingLocalValue = loadLocalConfigOverride(),
    )
  }

  data class ViewState(
    val featureConfig: FeatureConfig = FeatureConfig(),
    val isUsingLocalValue: Boolean = false,
  )
}

class SaveFeatureFlagValue(
  private val sharedPreferences: SharedPreferences,
) {
  operator fun invoke(flag: FeatureFlag<*>, value: Any) {
    val editor = sharedPreferences.edit()
    when (flag.type) {
      FeatureFlagType.FloatingPoint -> {
        editor.putFloat(flag.key, value as Float)
      }
      FeatureFlagType.Logical -> {
        editor.putBoolean(flag.key, value as Boolean)
      }
      FeatureFlagType.Numeric -> {
        editor.putLong(flag.key, value as Long)
      }
      FeatureFlagType.Text -> {
        editor.putString(flag.key, value as String)
      }
    }
    editor.commit()
  }
}

class SaveLocalConfigOverride(
  private val sharedPreferences: SharedPreferences,
) {
  operator fun invoke(newValue: Boolean) {
    sharedPreferences.edit(commit = true) {
      putBoolean(LocalFeatureConfigOverrideFlag, newValue)
    }
  }
}

class LoadLocalConfigOverride(
  private val sharedPreferences: SharedPreferences,
) {
  operator fun invoke(): Boolean =
    sharedPreferences.getBoolean(LocalFeatureConfigOverrideFlag, false)
}
