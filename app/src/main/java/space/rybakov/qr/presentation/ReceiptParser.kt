package space.rybakov.qr.presentation

import kotlinx.datetime.LocalDateTime

object ReceiptParser {
    fun parse(str: String): Receipt? {
        val map = str.split('&').map { param ->
            val pair = param.split('=')
            if (pair.size != 2) return null
            pair[0] to pair[1]
        }.toMap()
        try {
            return Receipt(
                dateTime = map["t"]?.parseDateTime().let { it ?: return null },
                summa = map["s"]?.toDouble() ?: return null,
                fn = map["fn"]?.toLong() ?: return null,
                fd = map["i"]?.toLong() ?: return null,
                fp = map["fp"]?.toLong() ?: return null,
                n = map["n"]?.toInt() ?: return null
            )
        } catch (e: Exception) {
            return null
        }
    }
}

private fun String.parseDateTime(): LocalDateTime? {
    val list = this.split('T')
    if (list.size != 2) return null
    return LocalDateTime(
        year = list[0].substring(0..3).toInt(),
        monthNumber = list[0].substring(4..5).toInt(),
        dayOfMonth = list[0].substring(6..7).toInt(),
        hour = list[1].substring(0..1).toInt(),
        minute = list[1].substring(2..3).toInt(),
        second = 0,
        nanosecond = 0
    )
}

