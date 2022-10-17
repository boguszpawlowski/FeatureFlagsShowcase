package io.github.boguszpawlowski.featureFlags.local.source

import android.content.SharedPreferences
import io.github.boguszpawlowski.featureFlags.source.FeatureSource

internal class LocalFeatureSource(
  private val sharedPreferences: SharedPreferences,
  private val localFeatureMapper: LocalFeatureMapper = LocalFeatureMapper(),
) : FeatureSource {

  override fun getFeatureConfig() = localFeatureMapper.map(sharedPreferences.all)
}
