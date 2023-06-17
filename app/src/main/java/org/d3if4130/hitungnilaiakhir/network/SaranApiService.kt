package org.d3if4130.hitungnilaiakhir.network

import com.google.gson.GsonBuilder
import org.d3if4130.hitungnilaiakhir.model.Saran
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/tiarasiahaan/api-hitungjitunilai/main/"

private val gson = GsonBuilder()
    .setLenient()
    .create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

interface SaranApiService {
    @GET("Saran.json")
    suspend fun getNilai(): Response<Saran>
}
object SaranApi{
    val service: SaranApiService by lazy {
        retrofit.create(SaranApiService::class.java)
    }
}

enum class ApiStatus {
    LOADING,
    SUCCESS,
    FAILED
}