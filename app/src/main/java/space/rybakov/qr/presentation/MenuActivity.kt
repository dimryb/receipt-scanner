package space.rybakov.qr.presentation

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import space.rybakov.qr.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerPermissionListener()

        binding.btnScanner.setOnClickListener {
            checkCameraPermission()
        }

        binding.btnQrGenerator.setOnClickListener {
            startActivity(Intent(this, QrGeneratorInputActivity::class.java))
        }

        binding.btnAccelerometer.setOnClickListener {

        }

        binding.btnGiroscope.setOnClickListener {

        }
    }

    private fun checkCameraPermission(){
        when{
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "Camera run from check", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ScannerActivity::class.java))
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                // Просит дать разрешение
                Toast.makeText(this, "We need your permission", Toast.LENGTH_LONG).show()
            }
            else -> {
                //pLauncher.launch(arrayOf(Manifest.permission.CAMERA))
                pLauncher.launch(arrayOf(android.Manifest.permission.CAMERA))
            }
        }
    }

    private fun registerPermissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            //После запроса о разрешении
            if(it[android.Manifest.permission.CAMERA] == true){
                Toast.makeText(this, "Camera run", Toast.LENGTH_LONG).show()
                val scannerIntent = Intent(this, ScannerActivity::class.java)
                startActivity(scannerIntent)
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}

