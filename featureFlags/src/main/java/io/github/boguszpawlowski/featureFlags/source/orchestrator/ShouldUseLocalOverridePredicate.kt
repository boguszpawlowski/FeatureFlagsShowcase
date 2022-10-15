package io.github.boguszpawlowski.featureFlags.source.orchestrator

fun interface ShouldUseLocalOverridePredicate {

    fun test(): Boolean
}
