package space.rybakov.qr

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.rybakov.qr.databinding.ActivityScannerResultBinding

class ScannerResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScannerResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arguments = intent.extras
        binding.tvScannerResult.text = arguments!!["result"].toString()
    }
}