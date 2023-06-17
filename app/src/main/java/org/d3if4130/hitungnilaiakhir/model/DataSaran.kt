package org.d3if4130.hitungnilaiakhir.model


import com.google.gson.annotations.SerializedName

data class DataSaran(
    @SerializedName("saran_A")
    val saranA: String?,
    @SerializedName("saran_B")
    val saranB: String?,
    @SerializedName("saran_C")
    val saranC: String?
)