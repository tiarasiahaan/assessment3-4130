package org.d3if4130.hitungnilaiakhir.ui.saran

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if4130.hitungnilaiakhir.model.DataSaran
import org.d3if4130.hitungnilaiakhir.network.ApiStatus
import org.d3if4130.hitungnilaiakhir.network.SaranApiService
import org.d3if4130.hitungnilaiakhir.network.UpdateWorker
import java.util.concurrent.TimeUnit

class SaranViewModel(private val api: SaranApiService) : ViewModel()  {

    var hasilNilai = MutableLiveData<List<DataSaran?>>()
    val getHasilNilai: LiveData<List<DataSaran?>> get() = (hasilNilai)
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData(){
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            Log.d("MainViewModel", "Loading Page")
            try {
                hasilNilai.postValue(api.getNilai().body()?.data!!)
                Log.d("MainViewModel", "Berhasil Digunakan")
                status.postValue(ApiStatus.SUCCESS)
            }catch (e:Exception){
                Log.d("MainViewModel", "Gagal: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getStatus(): LiveData<ApiStatus> = status

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            SaranFragment.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}