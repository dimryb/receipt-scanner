package space.rybakov.qr.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import space.rybakov.qr.R
import space.rybakov.qr.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>

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

        registerPermissionListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnScanner.setOnClickListener { checkCameraPermission() }
            btnQrGenerator.setOnClickListener { launchGeneratorFragment() }
            btnAccelerometer.setOnClickListener { launchAccelerometerFragment() }
            btnGyroscope.setOnClickListener { launchLevelFragment() }
        }
    }

    private fun launchScannerFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ScannerFragment.newInstance())
            .addToBackStack(ScannerFragment.NAME)
            .commit()
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

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(requireActivity(), "Camera run from check", Toast.LENGTH_LONG).show()
                launchScannerFragment()
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                // Просит дать разрешение
                Toast.makeText(requireActivity(), "We need your permission", Toast.LENGTH_LONG)
                    .show()
            }
            else -> {
                pLauncher.launch(arrayOf(android.Manifest.permission.CAMERA))
            }
        }
    }

    private fun registerPermissionListener() {
        pLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                //После запроса о разрешении
                if (it[android.Manifest.permission.CAMERA] == true) {
                    Toast.makeText(requireActivity(), "Camera run", Toast.LENGTH_LONG).show()
                    launchScannerFragment()
                } else {
                    Toast.makeText(requireActivity(), "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
    }

    companion object {
        const val NAME = "MenuFragment"

        fun newInstance(): MenuFragment {
            return MenuFragment()
        }
    }
}