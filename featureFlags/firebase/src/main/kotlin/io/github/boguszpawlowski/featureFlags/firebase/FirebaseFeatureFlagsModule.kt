package io.github.boguszpawlowski.featureFlags.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.github.boguszpawlowski.featureFlags.firebase.source.FirebaseFeatureSource
import io.github.boguszpawlowski.featureFlags.source.FeatureSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val firebaseFeatureFlagModule = module {
  single<FeatureSource>(named("Firebase")) {
    FirebaseFeatureSource(
      remoteConfig = FirebaseRemoteConfig.getInstance(),
      isDebug = get(named("isDebug")),
    )
  }
}

class Marker
