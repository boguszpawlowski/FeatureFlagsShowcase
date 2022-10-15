package io.github.boguszpawlowski.featureFlags.local.di

import android.content.Context
import io.github.boguszpawlowski.featureFlags.Feature
import io.github.boguszpawlowski.featureFlags.local.source.LocalFeatureFlagOverridePredicate
import io.github.boguszpawlowski.featureFlags.local.source.LocalFeatureSource
import io.github.boguszpawlowski.featureFlags.source.FeatureSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val localFeatureFlagsModule = module {
  single<FeatureSource>(named("Local")) {
    LocalFeatureSource(
      sharedPreferences = get(named("FeaturePrefs")),
    )
  }

  single {
    LocalFeatureFlagOverridePredicate(
      sharedPreferences = get(named("GeneralPrefs")),
    )
  }

  single(named("GeneralPrefs")) {
    get<Context>().getSharedPreferences("GeneralPrefs", Context.MODE_PRIVATE)
  }
  single(named("FeaturePrefs")) {
    get<Context>().getSharedPreferences("FeaturePrefs", Context.MODE_PRIVATE)
  }
}
