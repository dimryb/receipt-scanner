package space.rybakov.qr.presentation

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LevelViewModel(
    private val application: Application,
) : ViewModel()  {

    private val sensorManager: SensorManager by lazy {
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val sensorRotation by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    private var sensorEventListener: SensorEventListener? = null

    private val _rotationX = MutableLiveData<Float>()
    val rotationX: LiveData<Float>
        get() = _rotationX

    private val _rotationY = MutableLiveData<Float>()
    val rotationY: LiveData<Float>
        get() = _rotationY

    private val _rotationZ = MutableLiveData<Float>()
    val rotationZ: LiveData<Float>
        get() = _rotationZ

    fun registerSensors(){
        sensorEventListener = object : SensorEventListener {
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

                        _rotationX.value = orientations[0]
                        _rotationY.value = orientations[1]
                        _rotationZ.value = orientations[2]
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {

            }
        }
    }

    fun registerListener(){
        sensorManager.registerListener(
            sensorEventListener,
            sensorRotation,
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    fun unregisterListener(){
        sensorManager.unregisterListener(sensorEventListener)
    }
}