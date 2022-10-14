package io.github.boguszpawlowski.featureFlagsShowcase

import android.app.Application
import com.spoton.featureflags.di.featureFlagModule
import com.spoton.featureflags.provider.FeatureFlagProvider
import org.koin.core.context.startKoin
import org.koin.dsl.module

class FeatureFlagsShowcaseApp: Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      modules(
        featureFlagModule,
      )
    }
  }
}

private val applicationModule = module {
}
