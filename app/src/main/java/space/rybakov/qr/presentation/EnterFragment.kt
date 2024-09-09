package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import space.rybakov.qr.R
import space.rybakov.qr.databinding.FragmentEnterBinding
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date
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
        val date = getDate() ?: return
        val time = getTime() ?: return
        val price = binding.editPrice.text.toString().toInt()
        val fn = binding.editFN.text.toString().toInt()
        val fd = binding.editFD.text.toString().toInt()
        val fp = binding.editFP.text.toString().toInt()

//        findNavController().navigate(
//            ScannerFragmentDirections.actionEnterFragmentToScannerResultFragment(result)
//        )
    }

    private fun getDate(): Date? {
        val textDate = binding.editTextDate.text.toString()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        dateFormat.isLenient = false

        return runCatching {
            dateFormat.parse(textDate)
        }.getOrElse {
            binding.editTextDate.error = getString(R.string.enter_correct_date)
            null
        }
    }

    private fun getTime(): Time? {
        val textTime = binding.editTextTime.text.toString()
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        dateFormat.isLenient = false

        val date = runCatching {
            dateFormat.parse(textTime)
        }.getOrElse {
            binding.editTextDate.error = getString(R.string.enter_correct_time)
            null
        } ?: return null

        return Time(date.time)
    }

}