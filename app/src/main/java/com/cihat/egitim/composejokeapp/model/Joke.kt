package com.cihat.egitim.composejokeapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Joke(
    val error: Boolean,
    val category: String,
    val type: String,
    val joke: String? = null,
    val setup: String? = null,
    val delivery: String? = null,
    val flags: Flags,
    val id: Int,
    val safe: Boolean,
    val lang: String
)
