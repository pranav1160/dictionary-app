package com.example.dictionaryapp.feature_dictionary.data.remote.dto

import com.example.dictionaryapp.feature_dictionary.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo


data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String,
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            origin = origin ?: "",
            word = word
        )
    }
}