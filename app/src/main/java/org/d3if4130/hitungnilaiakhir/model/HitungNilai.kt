package org.d3if4130.hitungnilaiakhir.model

import org.d3if4130.hitungnilaiakhir.db.NilaiEntity

fun NilaiEntity.hitungNilai() : HasilNilai{
    val nama = nama
    val hasil = (0.20 * hadir) + (0.25 * tugas) + (0.25 * uts) + (0.30 * uas)
    val huruf =
        when {
            hasil >= 85 -> KategoriNilai.A
            hasil >= 70 && hasil <= 85 -> KategoriNilai.B
            else -> KategoriNilai.C
        }
    return HasilNilai(nama, hasil, huruf)
}