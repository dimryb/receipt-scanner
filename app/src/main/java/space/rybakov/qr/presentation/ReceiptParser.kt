package space.rybakov.qr.presentation

import kotlinx.datetime.LocalDateTime


object ReceiptParser {
    fun parse2(str: String): ReceiptResult? {
        val map = str.split('&').map { param ->
            val pair = param.split('=')
            if (pair.size != 2) return null
            pair[0] to pair[1]
        }.toMap()
        try {
            val date = map["t"]?.parseDateTime() ?: return null
            return ReceiptResult(
                type = ContentType.Receipt,
                text = str,
                dateString = String.format(locale = null,"%02d.%02d.%04d", date.dayOfMonth, date.monthNumber, date.year),
                timeString = String.format(locale = null,"%02d:%02d", date.hour, date.minute),
                summa = map["s"]?.toDouble() ?: return null,
                fn = map["fn"]?.toLong() ?: return null,
                fd = map["i"]?.toLong() ?: return null,
                fp = map["fp"]?.toLong() ?: return null,
                n = map["n"]?.toInt() ?: return null,
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

