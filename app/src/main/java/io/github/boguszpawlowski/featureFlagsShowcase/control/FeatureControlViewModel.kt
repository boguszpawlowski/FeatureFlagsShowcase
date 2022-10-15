package io.github.boguszpawlowski.featureFlagsShowcase.control

import androidx.lifecycle.ViewModel
import io.github.boguszpawlowski.featureFlags.FeatureFlag
import io.github.boguszpawlowski.featureFlags.config.FeatureConfig
import io.github.boguszpawlowski.featureFlags.provider.FeatureFlagProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

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
