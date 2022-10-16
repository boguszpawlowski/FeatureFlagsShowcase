package io.github.boguszpawlowski.featureFlagsShowcase.feature

import androidx.lifecycle.ViewModel
import io.github.boguszpawlowski.featureFlags.FeatureFlag
import io.github.boguszpawlowski.featureFlags.provider.FeatureFlagProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeatureViewModel(
  private val featureFlagProvider: FeatureFlagProvider,
) : ViewModel() {

  private val _viewState = MutableStateFlow(ViewState())
  val viewState: StateFlow<ViewState> = _viewState

  init {
    fetchFeatureConfig()
  }

  private fun fetchFeatureConfig() {
    val featureConfig = featureFlagProvider.get()

    _viewState.value = _viewState.value.copy(
      isFeature1Enabled = featureConfig[FeatureFlag.IsFeature1Enabled],
      buttonName = featureConfig[FeatureFlag.ButtonName],
      analyticsFraction = featureConfig[FeatureFlag.AnalyticSessionFraction],
      adsNumber = featureConfig[FeatureFlag.AdsNumber],
    )
  }

  data class ViewState(
    val isFeature1Enabled: Boolean = false,
    val buttonName: String = "",
    val analyticsFraction: Float = 0f,
    val adsNumber: Long = 0,
  )
}
