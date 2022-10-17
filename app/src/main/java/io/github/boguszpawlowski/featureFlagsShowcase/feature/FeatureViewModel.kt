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
    featureFlagProvider.get().let { featureFlag ->
      _viewState.value = _viewState.value.copy(
        isFeature1Enabled = featureFlag[FeatureFlag.IsFeature1Enabled],
        buttonName = featureFlag[FeatureFlag.ButtonName],
        analyticsFraction = featureFlag[FeatureFlag.AnalyticSessionFraction],
        adsNumber = featureFlag[FeatureFlag.AdsNumber],
      )
    }
  }

  data class ViewState(
    val isFeature1Enabled: Boolean = false,
    val buttonName: String = "",
    val analyticsFraction: Float = 0f,
    val adsNumber: Long = 0,
  )
}
