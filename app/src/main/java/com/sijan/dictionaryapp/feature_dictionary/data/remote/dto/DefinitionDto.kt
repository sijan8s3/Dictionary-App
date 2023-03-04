package com.sijan.dictionaryapp.feature_dictionary.data.remote.dto

import com.sijan.dictionaryapp.feature_dictionary.domain.model.Definition

data class DefinitionDto(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
) {
    fun toDefinition():Definition{
        return Definition(
            antonyms,definition,example, synonyms
        )
    }
}