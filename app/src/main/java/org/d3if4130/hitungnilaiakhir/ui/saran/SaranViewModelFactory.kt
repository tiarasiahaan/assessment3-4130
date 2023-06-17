package org.d3if4130.hitungnilaiakhir.ui.saran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if4130.hitungnilaiakhir.network.SaranApiService
import java.lang.IllegalArgumentException

class SaranViewModelFactory (
    private val api: SaranApiService
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaranViewModel::class.java)) {
            return SaranViewModel(api) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}