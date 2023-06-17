package org.d3if4130.hitungnilaiakhir.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4130.hitungnilaiakhir.db.NilaiDao
import org.d3if4130.hitungnilaiakhir.db.NilaiEntity
import org.d3if4130.hitungnilaiakhir.model.HasilNilai
import org.d3if4130.hitungnilaiakhir.model.KategoriNilai
import org.d3if4130.hitungnilaiakhir.model.hitungNilai

class HitungViewModel (private val db:NilaiDao) : ViewModel() {
    private val hasilNilai = MutableLiveData<HasilNilai?>()
    private val navigasi = MutableLiveData<KategoriNilai?>()

    fun hitungNilai(nama: String, hadir: Float, tugas: Float, uts: Float, uas : Float) {
        val dataNilai = NilaiEntity(
            nama = nama,
            hadir = hadir,
            tugas = tugas,
            uts = uts,
            uas = uas
        )
        hasilNilai.value = dataNilai.hitungNilai()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataNilai)
            }
        }
    }

    fun selesaiNavigasi(){
        navigasi.value = null
    }

    fun getNavigasi() : LiveData<KategoriNilai?> = navigasi

    fun mulaiNavigasi(){
        navigasi.value = hasilNilai.value?.kategoriNilai
    }

    fun getHasilNilai(): LiveData<HasilNilai?> = hasilNilai
}