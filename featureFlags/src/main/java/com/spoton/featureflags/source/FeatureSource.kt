package com.spoton.featureflags.source

import com.spoton.featureflags.config.FeatureConfig

interface FeatureSource {
    fun getFeatureConfig(): FeatureConfig
}
