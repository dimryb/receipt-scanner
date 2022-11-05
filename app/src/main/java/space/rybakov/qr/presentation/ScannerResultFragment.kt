package space.rybakov.qr.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import space.rybakov.qr.R
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

        resultView()

        return binding.root
    }

    private fun resultView() {
        val textViewResult: TextView = binding.tvScannerResult
        if (URLUtil.isNetworkUrl(args.text)) {
            // Todo: переделать на material design
            textViewResult.setTextColor(resources.getColor(R.color.purple_500, null))
            textViewResult.setOnClickListener {
                Toast.makeText(context, "Click: ${args.text}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.text)))
            }
        }
        textViewResult.text = args.text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}