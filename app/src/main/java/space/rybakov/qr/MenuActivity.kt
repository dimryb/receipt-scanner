package space.rybakov.qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import space.rybakov.qr.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanner.setOnClickListener {

        }

        binding.btnQrGenerator.setOnClickListener {

        }

        binding.btnAccelerometer.setOnClickListener {

        }

        binding.btnGiroscope.setOnClickListener {

        }
    }
}