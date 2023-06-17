package org.d3if4130.hitungnilaiakhir.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4130.hitungnilaiakhir.db.NilaiDao

class HistoriViewModel(private val db: NilaiDao) : ViewModel() {
    val data = db.getLastNilai()

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            db.clearData()
        }
    }
}