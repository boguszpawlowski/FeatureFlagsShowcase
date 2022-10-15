package io.github.boguszpawlowski.featureFlagsShowcase

import android.app.Application
import io.github.boguszpawlowski.featureFlags.di.featureFlagModule
import io.github.boguszpawlowski.featureFlags.firebase.firebaseFeatureFlagModule
import io.github.boguszpawlowski.featureFlags.local.di.localFeatureFlagsModule
import io.github.boguszpawlowski.featureFlagsShowcase.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class FeatureFlagsShowcaseApp : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(applicationContext)
      modules(
        featureFlagModule,
        applicationModule,
        localFeatureFlagsModule,
        firebaseFeatureFlagModule,
      )
    }

    Timber.plant(DebugTree())
  }
}
