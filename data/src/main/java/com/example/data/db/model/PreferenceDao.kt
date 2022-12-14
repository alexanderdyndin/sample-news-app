package com.example.data.db.model

import kotlinx.serialization.Serializable
import org.kodein.db.model.orm.Metadata

@Serializable
data class PreferenceDao(
    override val id: String,
    val value: String
) : Metadata