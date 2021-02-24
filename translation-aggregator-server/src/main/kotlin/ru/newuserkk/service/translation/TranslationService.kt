package ru.newuserkk.service.translation

import ru.newuserkk.common.OperationResult
import ru.newuserkk.model.translation.TranslationResult

interface TranslationService {
    suspend fun translate(word: String): OperationResult<List<TranslationResult>>
}
