package com.spoton.featureflags.source.orchestrator

fun interface ShouldUseLocalOverridePredicate {

    fun test(): Boolean
}
