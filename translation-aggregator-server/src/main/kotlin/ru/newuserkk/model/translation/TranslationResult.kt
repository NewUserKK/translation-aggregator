package ru.newuserkk.model.translation

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.ObjectCodec
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

sealed class TranslationResult

data class RegularTranslationResults(val translations: List<RegularTranslationResult>)

data class RegularTranslationResult(
    val text: String,
): TranslationResult()


data class UrbanTranslationResults(val list: List<UrbanTranslationResult>)

data class UrbanTranslationResult(
    val word: String,
    val definition: String,
    val example: String,
    @JsonProperty("defid") val defId: Int
): TranslationResult()
