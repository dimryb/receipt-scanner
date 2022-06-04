package space.rybakov.qr.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.RuntimeException

class LevelViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LevelViewModel::class.java)) {
            return LevelViewModel(application) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}