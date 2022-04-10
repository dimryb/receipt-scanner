package space.rybakov.qr

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.WriterException
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    var im: ImageView? = null
    var bGenerate: Button? = null
    var bScanner: Button? = null
    var textView: TextView? = null
    var counter: Int = 0
    var scannerIntent: Intent? = null
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>
    lateinit var sManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        im = findViewById(R.id.imageView)
        bGenerate = findViewById(R.id.button)
        bScanner = findViewById(R.id.bScan)
        bGenerate?.setOnClickListener{
            generateQrCode("папуличка любимый! $counter")
            ++counter
        }
        textView = findViewById(R.id.textView)
        textView?.text = "Текст сканера"
        registerPermissionListener()
        bScanner?.setOnClickListener{
            checkCameraPermission()
        }
        registerSensorAccelerometer()
    }

    private fun registerSensorAccelerometer(){
        val tvSensor = findViewById<TextView>(R.id.tvSensor)
        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val sListener = object : SensorEventListener{
            override fun onSensorChanged(sEvent: SensorEvent?) {
                val value = sEvent?.values
                val sData = "Гироскоп:\nX: ${value?.get(0)}\nY: ${value?.get(1)}\nZ: ${value?.get(2)}"
                tvSensor.text = sData
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                TODO("Not yet implemented")
            }
        }
        sManager.registerListener(sListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun generateQrCode(text: String){
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 600)
        try {
            val bMap = qrGenerator.encodeAsBitmap()
            im?.setImageBitmap(bMap)
        }catch (e: WriterException){

        }
    }

    private fun checkCameraPermission(){
        when{
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(this, "Camera run", Toast.LENGTH_LONG).show()
                    scannerIntent = Intent(this, ScannerActivity::class.java)
                    startActivity(scannerIntent)
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

    /**
     * После запроса о разрешении
     */
    private fun registerPermissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if(it[android.Manifest.permission.CAMERA] == true){
                Toast.makeText(this, "Camera run", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}