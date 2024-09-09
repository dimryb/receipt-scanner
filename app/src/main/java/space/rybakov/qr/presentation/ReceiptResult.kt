package space.rybakov.qr.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ReceiptResult(
    val type: ContentType,
    val text: String = "",
    val dateString: String = "",
    val timeString: String = "",
    val summa: Double = 0.0,
    val fn: Long = 0,
    val fd: Long = 0,
    val fp: Long = 0,
    val n: Int = 0,
) : Parcelable
