package io.github.boguszpawlowski.featureFlags.source.orchestrator

import io.github.boguszpawlowski.featureFlags.config.FeatureConfig
import io.github.boguszpawlowski.featureFlags.source.FeatureSource

internal class DebugFeatureSourceOrchestrator(
  private val shouldUseLocalOverride: ShouldUseLocalOverridePredicate,
  private val localSource: FeatureSource,
  private val remoteSource: FeatureSource,
) : FeatureSourceOrchestrator {

    override fun getFeatureConfig(): FeatureConfig {
        return when (shouldUseLocalOverride.test()) {
            true -> localSource.getFeatureConfig()
            false -> remoteSource.getFeatureConfig()
        }
    }
}
