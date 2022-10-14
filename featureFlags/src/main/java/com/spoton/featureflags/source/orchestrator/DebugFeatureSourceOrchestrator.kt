package com.spoton.featureflags.source.orchestrator

import com.spoton.featureflags.config.FeatureConfig
import com.spoton.featureflags.source.FeatureSource

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
