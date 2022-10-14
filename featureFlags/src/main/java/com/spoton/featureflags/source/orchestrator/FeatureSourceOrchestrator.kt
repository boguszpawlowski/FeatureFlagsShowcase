package com.spoton.featureflags.source.orchestrator

import com.spoton.featureflags.config.FeatureConfig
import com.spoton.featureflags.source.FeatureSource

internal interface FeatureSourceOrchestrator : FeatureSource {
    override fun getFeatureConfig(): FeatureConfig
}
