package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import space.rybakov.qr.databinding.FragmentScannerResultBinding

class ScannerResultFragment : Fragment() {

    private val args by navArgs<ScannerResultFragmentArgs>()

    private var _binding: FragmentScannerResultBinding? = null
    private val binding: FragmentScannerResultBinding
        get() = _binding ?: throw RuntimeException("FragmentScannerResultBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerResultBinding.inflate(inflater, container, false)
        binding.tvScannerResult.text = args.text
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}