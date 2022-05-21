package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import space.rybakov.qr.R
import space.rybakov.qr.databinding.FragmentTextInputBinding

class TextInputFragment : Fragment() {
    private var _binding: FragmentTextInputBinding? = null
    private val binding: FragmentTextInputBinding
        get() = _binding ?: throw RuntimeException("FragmentTextInputBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnGenerateQr.setOnClickListener { launchQrGenerator() }
        }
    }

    private fun launchQrGenerator(){
        val text = binding.etInput.text.toString()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, QrGeneratorFragment.newInstance(text))
            .addToBackStack(QrGeneratorFragment.NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME = "TextInputFragment"

        fun newInstance(): TextInputFragment {
            return TextInputFragment()
        }
    }
}