package space.rybakov.qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.zxing.WriterException
import space.rybakov.qr.databinding.ActivityQrGeneratorBinding

class QrGeneratorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrGeneratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arguments = intent.extras
        generateQrCode(arguments!!["generate"].toString())
    }

    private fun generateQrCode(text: String) {
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 600)
        try {
            val bMap = qrGenerator.encodeAsBitmap()
            binding.ivQrGenerator?.setImageBitmap(bMap)
        } catch (e: WriterException) {

        }
    }
}