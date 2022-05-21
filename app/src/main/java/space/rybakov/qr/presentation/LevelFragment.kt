package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import space.rybakov.qr.databinding.FragmentLevelBinding

class LevelFragment : Fragment() {

    private var _binding: FragmentLevelBinding? = null
    private val binding: FragmentLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME = "LevelFragment"

        fun newInstance(): LevelFragment {
            return LevelFragment()
        }
    }
}