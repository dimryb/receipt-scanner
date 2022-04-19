package space.rybakov.qr

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.Sensor.TYPE_ROTATION_VECTOR
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

    lateinit var sManager: SensorManager
    var sensorRotation: Sensor? = null
    var sensorAccelerometer: Sensor? = null
    var sv: SensorEventListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menuIntent = Intent(this, MenuActivity::class.java)
        startActivity(menuIntent)

        im = findViewById(R.id.imageView)
        bGenerate = findViewById(R.id.button)
        bGenerate?.setOnClickListener{
            generateQrCode("папуличка любимый! $counter")
            ++counter
        }
        textView = findViewById(R.id.textView)
        textView?.text = "Текст сканера"

        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        registerSensors()
    }

    private fun registerSensors(){
        val tvAngle = findViewById<TextView>(R.id.tvAngle)
        val ivLevel = findViewById<ImageView>(R.id.ivLevel)
        val tvSensor = findViewById<TextView>(R.id.tvSensor)
        sensorRotation = sManager.getDefaultSensor(TYPE_ROTATION_VECTOR)
        sensorAccelerometer = sManager.getDefaultSensor(TYPE_ACCELEROMETER)
        sv = object : SensorEventListener{
            override fun onSensorChanged(sEvent: SensorEvent?) {
                when{
                    sEvent?.sensor == sensorRotation -> {
                        val rotationMatrix = FloatArray(16)
                        SensorManager.getRotationMatrixFromVector(rotationMatrix, sEvent?.values)
                        val remappedRotationMatrix = FloatArray(16)
                        SensorManager.remapCoordinateSystem(rotationMatrix,
                            SensorManager.AXIS_X,
                            SensorManager.AXIS_Z,
                            remappedRotationMatrix
                        )
                        val orientations = FloatArray(16)
                        SensorManager.getOrientation(remappedRotationMatrix, orientations)
                        for(i in 0..3 ){
                            orientations[i] = Math.toDegrees(orientations[i].toDouble()).toFloat()
                        }

                        val sData = "X: " + String.format("%.1f°", orientations[0]) + "\n" +
                                "Y: " + String.format("%.1f°", orientations[1]) + "\n" +
                                "Z: " + String.format("%.1f°", orientations[2]) + "\n"
                        tvAngle.text = sData
                        ivLevel.rotation = -orientations[2]
                    }
                    sEvent?.sensor == sensorAccelerometer -> {
                        val gravitationalAcceleration = 9.81
                        val accelerationX = sEvent?.values?.get(0)?.div(gravitationalAcceleration)
                        val accelerationY = sEvent?.values?.get(1)?.div(gravitationalAcceleration)
                        val accelerationZ = sEvent?.values?.get(2)?.div(gravitationalAcceleration)
                        val sData = "X: " + String.format("%.2fg", accelerationX) + "\n" +
                                "Y: " + String.format("%.2fg", accelerationY) + "\n" +
                                "Z: " + String.format("%.2fg", accelerationZ) + "\n"
                        tvSensor.text = sData
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
                //sManager.unregisterListener(sv, sensorRotation)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        sManager.registerListener(sv, sensorRotation, SensorManager.SENSOR_DELAY_FASTEST)
        sManager.registerListener(sv, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sManager.unregisterListener(sv)
    }

    private fun generateQrCode(text: String){
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 600)
        try {
            val bMap = qrGenerator.encodeAsBitmap()
            im?.setImageBitmap(bMap)
        }catch (e: WriterException){

        }
    }
}