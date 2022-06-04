package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.zxing.WriterException
import space.rybakov.qr.databinding.FragmentQrGeneratorBinding


class QrGeneratorFragment : Fragment() {

    private val args by navArgs<QrGeneratorFragmentArgs>()

    private var _binding: FragmentQrGeneratorBinding? = null
    private val binding: FragmentQrGeneratorBinding
        get() = _binding ?: throw RuntimeException("FragmentQrGeneratorBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrGeneratorBinding.inflate(inflater, container, false)
        generateQrCode(args.text)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateQrCode(text: String) {
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 600)
        try {
            val bMap = qrGenerator.encodeAsBitmap()
            binding.ivQrGenerator.setImageBitmap(bMap)
        } catch (e: WriterException) {

        }
    }
}