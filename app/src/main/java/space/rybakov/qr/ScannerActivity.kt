package space.rybakov.qr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {
    private lateinit var zbView: ZBarScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zbView = ZBarScannerView(this)
        setContentView(zbView)
    }

    override fun onResume() {
        super.onResume()
        zbView.setResultHandler(this)
        zbView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zbView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        Log.d("MyLog", "Result: ${result?.contents}")
        //Toast.makeText(this, "Result: ${result?.contents}", Toast.LENGTH_LONG).show()

        val scannerResultIntent = Intent(this, ScannerResultActivity::class.java)
        scannerResultIntent.putExtra("result", "${result?.contents}");
        startActivity(scannerResultIntent)

        finish()
    }
}