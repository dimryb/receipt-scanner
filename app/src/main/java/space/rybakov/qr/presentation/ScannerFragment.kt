package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import space.rybakov.qr.R

class ScannerFragment : Fragment(), ZBarScannerView.ResultHandler {

    private lateinit var zbView: ZBarScannerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        zbView = ZBarScannerView(requireActivity())
        return zbView
    }

    override fun onResume() {
        super.onResume()

        zbView.setResultHandler(this)
        zbView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zbView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        val text: String = result?.contents.toString()
        launchScannerResult(text)
    }

    private fun launchScannerResult(text : String){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ScannerResultFragment.newInstance(text))
            .addToBackStack(ScannerResultFragment.NAME)
            .commit()
    }

    companion object {
        const val NAME = "ScannerFragment"

        fun newInstance(): ScannerFragment {
            return ScannerFragment()
        }
    }
}