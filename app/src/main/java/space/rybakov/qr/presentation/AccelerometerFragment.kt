package space.rybakov.qr.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import space.rybakov.qr.databinding.FragmentAccelerometerBinding

class AccelerometerFragment : Fragment() {

    private lateinit var sManager: SensorManager
    private var sensorAccelerometer: Sensor? = null
    private var sensorEventListener: SensorEventListener? = null

    private var _binding: FragmentAccelerometerBinding? = null
    private val binding: FragmentAccelerometerBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccelerometerBinding.inflate(inflater, container, false)
        sManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        registerAccelerometer()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sManager.registerListener(sensorEventListener, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sManager.unregisterListener(sensorEventListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun registerAccelerometer(){
        sensorAccelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(sEvent: SensorEvent?) {
                when (sEvent?.sensor) {
                    sensorAccelerometer -> {
                        val gravitationalAcceleration = 9.81
                        val accelerationX = sEvent?.values?.get(0)?.div(gravitationalAcceleration)
                        val accelerationY = sEvent?.values?.get(1)?.div(gravitationalAcceleration)
                        val accelerationZ = sEvent?.values?.get(2)?.div(gravitationalAcceleration)
                        val sData = "X: " + String.format("%.2fg", accelerationX) + "\n" +
                                "Y: " + String.format("%.2fg", accelerationY) + "\n" +
                                "Z: " + String.format("%.2fg", accelerationZ) + "\n"
                        binding.tvSensor.text = sData
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }
    }
}