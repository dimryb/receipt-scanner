package space.rybakov.qr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import space.rybakov.qr.databinding.ActivityQrGeneratorInputBinding

class QrGeneratorInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrGeneratorInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrGeneratorInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerateQr.setOnClickListener {
            val intent = Intent(this, QrGeneratorActivity::class.java)
            intent.putExtra("generate", "${binding.etInput.text}");
            startActivity(intent)
        }
    }
}