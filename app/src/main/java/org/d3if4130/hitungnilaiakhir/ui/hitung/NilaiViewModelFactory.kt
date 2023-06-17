package org.d3if4130.hitungnilaiakhir.ui.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if4130.hitungnilaiakhir.db.NilaiDao

class HitungViewModelFactory (private val db: NilaiDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HitungViewModel::class.java)){
            return HitungViewModel(db) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}