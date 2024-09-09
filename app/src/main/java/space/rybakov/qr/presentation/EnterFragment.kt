package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import space.rybakov.qr.R
import space.rybakov.qr.databinding.FragmentEnterBinding
import java.text.SimpleDateFormat
import java.util.Locale


class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val binding: FragmentEnterBinding
        get() = _binding ?: throw RuntimeException("FragmentEnterBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterBinding.inflate(inflater, container, false)

        binding.enterShare.setOnClickListener {
            share()
        }

        return binding.root
    }

    private fun share() {
        val textDate = binding.editTextDate.text.toString()
        val textTime = binding.editTextTime.text.toString()
        val invalidDate = !validDate(textDate)
        val invalidTime = !validTime(textTime)

        if (invalidDate) {
            binding.editTextDate.error = getString(R.string.enter_correct_date)
        }

        if (invalidTime) {
            binding.editTextTime.error = getString(R.string.enter_correct_time)
        }

        if (invalidDate || invalidTime) return

        val receipt = ReceiptResult(
            type = ContentType.Receipt,
            dateString = textDate,
            timeString = textTime,
            summa = binding.editPrice.text.toString().toDouble(),
            fn = binding.editFN.text.toString().toLong(),
            fd = binding.editFD.text.toString().toLong(),
            fp = binding.editFP.text.toString().toLong()
        )

        findNavController().navigate(
            EnterFragmentDirections.actionEnterFragmentToScannerResultFragment(receipt)
        )
    }

    private fun validDate(textDate: String): Boolean {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        dateFormat.isLenient = false

        return runCatching {
            dateFormat.parse(textDate)
            true
        }.getOrElse {
            false
        }
    }

    private fun validTime(textTime: String): Boolean {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        dateFormat.isLenient = false

        return runCatching {
            dateFormat.parse(textTime)
            true
        }.getOrElse {
            false
        }
    }

}