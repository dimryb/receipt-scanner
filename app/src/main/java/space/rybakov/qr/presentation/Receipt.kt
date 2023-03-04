package space.rybakov.qr.presentation

import kotlinx.datetime.LocalDateTime

data class Receipt(
    val dataTime: LocalDateTime? = null,
    val summa: Double,
    val fn: Long,
    val fd: Long,
    val fp: Long,
    val n: Int,
)
