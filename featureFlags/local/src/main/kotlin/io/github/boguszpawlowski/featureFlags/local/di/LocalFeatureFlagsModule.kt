package io.github.boguszpawlowski.featureFlags.local.di

import android.content.Context
import io.github.boguszpawlowski.featureFlags.local.source.LocalFeatureFlagOverridePredicate
import io.github.boguszpawlowski.featureFlags.local.source.LocalFeatureSource
import io.github.boguszpawlowski.featureFlags.source.FeatureSource
import io.github.boguszpawlowski.featureFlags.source.orchestrator.ShouldUseLocalOverridePredicate
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val GeneralPrefs = "GeneralPrefs"
const val FeaturePrefs = "FeaturePrefs"

val localFeatureFlagsModule = module {
  single<FeatureSource>(named("Local")) {
    LocalFeatureSource(
      sharedPreferences = get(named(FeaturePrefs)),
    )
  }

  single<ShouldUseLocalOverridePredicate> {
    LocalFeatureFlagOverridePredicate(
      sharedPreferences = get(named(GeneralPrefs)),
    )
  }

  single(named(GeneralPrefs)) {
    get<Context>().getSharedPreferences(GeneralPrefs, Context.MODE_PRIVATE)
  }
  single(named(FeaturePrefs)) {
    get<Context>().getSharedPreferences(FeaturePrefs, Context.MODE_PRIVATE)
  }
}
