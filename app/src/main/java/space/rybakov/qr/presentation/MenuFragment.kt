package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import space.rybakov.qr.R
import space.rybakov.qr.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnScanner.setOnClickListener { launchScannerFragment() }
            btnQrGenerator.setOnClickListener { launchGeneratorFragment() }
            btnAccelerometer.setOnClickListener { launchAccelerometerFragment() }
            btnGyroscope.setOnClickListener { launchLevelFragment() }
        }
    }

    private fun launchScannerFragment() {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, ScannerFragment.newInstance())
//            .addToBackStack(ScannerFragment.NAME)
//            .commit()
    }

    private fun launchGeneratorFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, TextInputFragment.newInstance())
            .addToBackStack(TextInputFragment.NAME)
            .commit()
    }

    private fun launchAccelerometerFragment() {

    }

    private fun launchLevelFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, LevelFragment.newInstance())
            .addToBackStack(LevelFragment.NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME = "MenuFragment"

        fun newInstance(): MenuFragment {
            return MenuFragment()
        }

    }
}