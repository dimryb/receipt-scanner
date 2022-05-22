package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import space.rybakov.qr.databinding.FragmentScannerResultBinding

class ScannerResultFragment : Fragment() {
    private lateinit var scannerResultText: String

    private var _binding: FragmentScannerResultBinding? = null
    private val binding: FragmentScannerResultBinding
        get() = _binding ?: throw RuntimeException("FragmentScannerResultBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerResultBinding.inflate(inflater, container, false)
        binding.tvScannerResult.text = scannerResultText
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        scannerResultText = requireArguments().getString(KEY_SCANNER_RESULT, "")
    }

    companion object {
        const val NAME = "ScannerResultFragment"
        private const val KEY_SCANNER_RESULT = "key_scanner_result"

        fun newInstance(text: String): ScannerResultFragment {

            return ScannerResultFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_SCANNER_RESULT, text)
                }
            }
        }
    }
}