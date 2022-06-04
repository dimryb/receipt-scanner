package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import space.rybakov.qr.databinding.FragmentLevelBinding

class LevelFragment : Fragment() {

    private val viewModelFactory by lazy {
        LevelViewModelFactory(requireActivity().application)
    }

    private val viewModel: LevelViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LevelViewModel::class.java]
    }

    private var _binding: FragmentLevelBinding? = null
    private val binding: FragmentLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        viewModel.registerSensors()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.registerListener()
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.rotation.observe(viewLifecycleOwner) {
            binding.rotation = it
        }
    }
}