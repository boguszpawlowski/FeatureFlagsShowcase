package io.github.boguszpawlowski.featureFlagsShowcase.di

import io.github.boguszpawlowski.featureFlags.local.di.FeaturePrefs
import io.github.boguszpawlowski.featureFlags.local.di.GeneralPrefs
import io.github.boguszpawlowski.featureFlagsShowcase.BuildConfig
import io.github.boguszpawlowski.featureFlagsShowcase.control.FeatureControlViewModel
import io.github.boguszpawlowski.featureFlagsShowcase.control.LoadLocalConfigOverride
import io.github.boguszpawlowski.featureFlagsShowcase.control.SaveFeatureFlagValue
import io.github.boguszpawlowski.featureFlagsShowcase.control.SaveLocalConfigOverride
import io.github.boguszpawlowski.featureFlagsShowcase.feature.FeatureViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
  viewModel {
    FeatureControlViewModel(
      saveFeatureFlagValue = get(),
      featureFlagProvider = get(),
      loadLocalConfigOverride = get(),
      saveLocalConfigOverride = get(),
    )
  }

  viewModel {
    FeatureViewModel(
      featureFlagProvider = get(),
    )
  }

  factory {
    SaveFeatureFlagValue(
      sharedPreferences = get(named(FeaturePrefs)),
    )
  }

  factory {
    LoadLocalConfigOverride(
      sharedPreferences = get(named(GeneralPrefs))
    )
  }

  factory {
    SaveLocalConfigOverride(
      sharedPreferences = get(named(GeneralPrefs))
    )
  }

  single(named("isDebug")) {
    BuildConfig.DEBUG
  }
}
