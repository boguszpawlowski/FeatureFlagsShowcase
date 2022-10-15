package io.github.boguszpawlowski.featureFlags.source

import io.github.boguszpawlowski.featureFlags.config.FeatureConfig

interface FeatureSource {
    fun getFeatureConfig(): FeatureConfig
}
