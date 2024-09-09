package space.rybakov.qr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    private fun launchScannerResult(text: String) {
        val result = if (URLUtil.isNetworkUrl(text)) {
            ReceiptResult(type = ContentType.Link, text = text)
        } else {
            ReceiptParser.parse2(text) ?: ReceiptResult(type = ContentType.Unknown, text = text)
        }

        findNavController().navigate(
            ScannerFragmentDirections.actionScannerFragmentToScannerResultFragment(result)
        )
    }
}