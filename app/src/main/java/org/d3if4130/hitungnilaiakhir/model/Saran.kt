package org.d3if4130.hitungnilaiakhir.model


import com.google.gson.annotations.SerializedName

data class Saran(
    @SerializedName("data")
    val `data`: List<DataSaran?>?
)