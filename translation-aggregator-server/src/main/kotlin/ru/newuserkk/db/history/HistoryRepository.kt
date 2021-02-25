package ru.newuserkk.db.history

import ru.newuserkk.model.auth.UserId

class HistoryRepository {
    private val requestHistory = mutableMapOf<UserId, LinkedHashSet<String>>()

    fun getHistory(userId: UserId): List<String> =
        requestHistory.getOrDefault(userId, linkedSetOf()).toList()

    fun addQueryToHistory(userId: UserId, query: String) {
        requestHistory.getOrPut(userId) { linkedSetOf() }.add(query)
    }
}
