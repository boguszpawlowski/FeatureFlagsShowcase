package io.github.boguszpawlowski.featureFlagsShowcase.di

import io.github.boguszpawlowski.featureFlagsShowcase.BuildConfig
import io.github.boguszpawlowski.featureFlagsShowcase.control.FeatureControlViewModel
import io.github.boguszpawlowski.featureFlagsShowcase.control.SaveFeatureFlagValue
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
  viewModel {
    FeatureControlViewModel(
      saveFeatureFlagValue = get(),
      featureFlagProvider = get(),
    )
  }

  factory {
    SaveFeatureFlagValue(
      sharedPreferences = get(named("FeaturePrefs")),
    )
  }

  single(named("isDebug")) {
    BuildConfig.DEBUG
  }
}
