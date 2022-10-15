package io.github.boguszpawlowski.featureFlags.source.orchestrator

import io.github.boguszpawlowski.featureFlags.config.FeatureConfig
import io.github.boguszpawlowski.featureFlags.source.FeatureSource

internal interface FeatureSourceOrchestrator : FeatureSource {
    override fun getFeatureConfig(): FeatureConfig
}
