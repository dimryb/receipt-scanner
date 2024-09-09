package space.rybakov.qr.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import space.rybakov.qr.R
import space.rybakov.qr.databinding.FragmentScannerResultBinding

class ScannerResultFragment : Fragment() {

    private val args by navArgs<ScannerResultFragmentArgs>()

    private var _binding: FragmentScannerResultBinding? = null
    private val binding: FragmentScannerResultBinding
        get() = _binding ?: throw RuntimeException("FragmentScannerResultBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerResultBinding.inflate(inflater, container, false)

        resultView(args.result)

        return binding.root
    }

    private fun resultView(result: ReceiptResult) {
        val textViewResult: TextView = binding.tvScannerResult
        val type = result.type

        binding.textViewTitle.text = when (type) {
            ContentType.Link -> "Это ссылка"
            ContentType.Receipt -> "Это чек"
            else -> "Это не извесно что"
        }

        textViewResult.text = when (type) {
            ContentType.Link -> result.text
            ContentType.Receipt -> {
                result.let {
                    """
                        |Дата: ${result.dateString}
                        |Время: ${result.timeString}
                        |Сумма: ${result.summa}
                        |ФН: ${result.fn}
                        |ФД: ${result.fd}
                        |ФП: ${result.fp}
                        |""".trimMargin()
                }
            }
            else -> result.text
        }

        if (type == ContentType.Link) {
            textViewResult.setTextColor(resources.getColor(R.color.purple_500, null))
            textViewResult.setOnClickListener {
                Toast.makeText(context, "Click: ${result.text}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(result.text)))
            }
            binding.browse.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(result.text)))
            }
        }

        if (type == ContentType.Receipt) {
            binding.share.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain")
                //intent.setPackage("Telegram")
                intent.putExtra(Intent.EXTRA_TEXT, "Чек: ${textViewResult.text}")
                startActivity(Intent.createChooser(intent, "Share with"))
            }
        }

        binding.again.setOnClickListener {
            val navController = findNavController()
            navController.popBackStack(R.id.menuFragment, false)
        }

        binding.browse.visibility = if (type == ContentType.Link) View.VISIBLE else View.GONE
        binding.share.visibility =
            if ((type == ContentType.Link) || (type == ContentType.Receipt)) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}