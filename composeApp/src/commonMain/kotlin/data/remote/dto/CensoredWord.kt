package data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CensoredWord(
    val result: String,
)
