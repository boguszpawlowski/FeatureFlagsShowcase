package io.github.boguszpawlowski.featureFlags.config

import io.github.boguszpawlowski.featureFlags.FeatureFlag

@Suppress("UNCHECKED_CAST")
class FeatureConfig(
  val featureFlags: Map<FeatureFlag<*>, *>,
) {
  constructor(
    vararg featureFlags: Pair<FeatureFlag<*>, *>,
  ) : this(featureFlags.toMap())

  operator fun <T : Any> get(featureFlag: FeatureFlag<T>): T = featureFlags[featureFlag] as T
}
