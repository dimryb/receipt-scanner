package space.rybakov.qr

import android.content.Intent
import android.content.pm.PackageManager
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
    private lateinit var pLauncher: ActivityResultLauncher<String>

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
                Toast.makeText(this, "We need your permission", Toast.LENGTH_LONG).show()
            }
            else -> {
                pLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
//            != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 12)
//        }else{
//            scannerIntent = Intent(this, ScannerActivity::class.java)
//            startActivity(scannerIntent)
//        }
    }

    /**
     * Вызовется при установке разрешения
     */
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == 12){
//            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                scannerIntent = Intent(this, ScannerActivity::class.java)
//                startActivity(scannerIntent)
//            }
//        }
//    }

    private fun registerPermissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                Toast.makeText(this, "Camera run", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}