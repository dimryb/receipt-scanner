package space.rybakov.qr.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ROTATION_VECTOR
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import space.rybakov.qr.databinding.FragmentLevelBinding

class LevelFragment : Fragment() {

    private lateinit var sManager: SensorManager
    private var sensorRotation: Sensor? = null
    private var sensorEventListener: SensorEventListener? = null

    private var _binding: FragmentLevelBinding? = null
    private val binding: FragmentLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        sManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        registerSensors()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sManager.registerListener(sensorEventListener, sensorRotation, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        sManager.unregisterListener(sensorEventListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun registerSensors(){
        sensorRotation = sManager.getDefaultSensor(TYPE_ROTATION_VECTOR)
        sensorEventListener = object : SensorEventListener{
            override fun onSensorChanged(sEvent: SensorEvent?) {
                when (sEvent?.sensor) {
                    sensorRotation -> {
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
                        binding.tvAngle.text = sData
                        binding.ivLevel.rotation = -orientations[2]
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {

            }
        }
    }
}