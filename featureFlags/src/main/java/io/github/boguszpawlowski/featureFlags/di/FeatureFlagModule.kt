package io.github.boguszpawlowski.featureFlags.di

import io.github.boguszpawlowski.featureFlags.provider.FeatureFlagProvider
import org.koin.dsl.module

val featureFlagModule = module {
  single {
    FeatureFlagProvider(
      featureSource = get(),
    )
  }
}
