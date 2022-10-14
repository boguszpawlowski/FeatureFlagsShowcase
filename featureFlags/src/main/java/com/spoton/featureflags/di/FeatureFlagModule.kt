package com.spoton.featureflags.di

import com.spoton.featureflags.provider.FeatureFlagProvider
import org.koin.dsl.module

val featureFlagModule = module {
  single {
    FeatureFlagProvider(
      featureSource = get(),
    )
  }
}
