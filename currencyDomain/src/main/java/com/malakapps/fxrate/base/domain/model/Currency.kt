package com.malakapps.fxrate.base.domain.model

import java.io.Serializable

data class Currency(
    val code: String,
    val name: String,
) : Serializable
