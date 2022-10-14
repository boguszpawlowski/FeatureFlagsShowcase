package com.spoton.featureflags.local.source

import android.os.OperationCanceledException
import com.spoton.featureflags.config.FeatureConfig
import com.spoton.featureflags.source.FeatureSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class LocalFeatureSource(
    localDefaultFeatureConfigProvider: LocalDefaultFeatureConfigProvider = LocalDefaultFeatureConfigProvider(),
    private val localFeatureConfigPersistence: LocalFeatureConfigPersistence,
    private val localFeatureMapper: LocalFeatureMapper = LocalFeatureMapper(),
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) : FeatureSource {

    private var featureConfig: FeatureConfig = localDefaultFeatureConfigProvider.provide()

    init {
        fetchLocalConfig()
    }

    override fun getFeatureConfig(): FeatureConfig =
        featureConfig.also {
            fetchLocalConfig()
        }

    private fun fetchLocalConfig() {
        scope.launch {
            featureConfig = localFeatureMapper.map(localFeatureConfigPersistence.get())
        }
    }
}

internal interface LocalFeatureConfigPersistence {
    suspend fun get(): Collection<LocalFeatureFlagWithValue>
}
