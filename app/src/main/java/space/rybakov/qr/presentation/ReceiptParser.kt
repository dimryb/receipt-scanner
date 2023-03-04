package space.rybakov.qr.presentation

object ReceiptParser {
    fun parse(str: String): Receipt? {
        val list = str.split('&')
        val map = list.map { param ->
            val pair = param.split('=')
            if (pair.size != 2) return null
            pair[0] to pair[1]
        }.toMap()

        try {
            val summa = map["s"]?.toDouble() ?: return null
            val fn = map["fn"]?.toLong() ?: return null
            val fd = map["i"]?.toLong() ?: return null
            val fp = map["fp"]?.toLong() ?: return null
            val n = map["n"]?.toInt() ?: return null
            return Receipt( summa = summa, fn = fn, fd = fd, fp= fp, n = n)
        } catch (e: Exception) {
            return null
        }
    }
}
