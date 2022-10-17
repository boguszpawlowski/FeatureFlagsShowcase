package io.github.boguszpawlowski.featureFlags.di

import io.github.boguszpawlowski.featureFlags.provider.FeatureFlagProvider
import io.github.boguszpawlowski.featureFlags.source.FeatureSource
import io.github.boguszpawlowski.featureFlags.source.orchestrator.DebugFeatureSourceOrchestrator
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureFlagModule = module {
  single {
    FeatureFlagProvider(
      featureSource = get(named("FeatureSource")),
    )
  }

  single<FeatureSource>(named("FeatureSource")) {
    val isDebug = get<Boolean>(named("isDebug"))

    if (isDebug) {
      DebugFeatureSourceOrchestrator(
        shouldUseLocalOverride = get(),
        localSource = get(named("Local")),
        remoteSource = get(named("Firebase")),
      )
    } else {
      get(named("RemoteSource"))
    }
  }
}
