package com.spoton.featureFlags.firebase.source

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.spoton.featureflags.config.FeatureConfig
import com.spoton.featureflags.source.FeatureSource
import timber.log.Timber

internal class FirebaseFeatureSource(
    private val firebaseFeatureMapper: FirebaseFeatureMapper = FirebaseFeatureMapper(),
    private val remoteConfig: FirebaseRemoteConfig,
    private val isDebug: Boolean,
) : FeatureSource {
    private var featureConfig: FeatureConfig = firebaseFeatureMapper.map(
        remoteValues = remoteConfig.all,
        isDebug = isDebug,
    ).also { Timber.d("[New Feature Config from Firebase Cache] \n $it") }

    init {
        fetchRemoteConfig()
    }

    override fun getFeatureConfig(): FeatureConfig =
        featureConfig.also {
            fetchRemoteConfig()
        }

    private fun fetchRemoteConfig() {
        remoteConfig.fetchAndActivate().addOnSuccessListener { isFetchedFromFirebase ->
            if (isFetchedFromFirebase.not()) {
                return@addOnSuccessListener
            }

            val newConfig = firebaseFeatureMapper.map(
                remoteValues = remoteConfig.all,
                isDebug = isDebug,
            )

            if (featureConfig != newConfig) {
                Timber.d("[New Feature Config from Firebase] \n $newConfig")
                featureConfig = newConfig
            }
        }.addOnFailureListener { exception ->
            Timber.e(exception, "Unable to load config from remote.")
        }
    }
}
