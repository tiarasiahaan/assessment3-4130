package org.d3if4130.hitungnilaiakhir.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NilaiDao {
    @Insert
    fun insert(nilai: NilaiEntity)
    @Query("SELECT * FROM nilai ORDER BY id DESC")
    fun getLastNilai(): LiveData<List<NilaiEntity?>>
    @Query("DELETE FROM nilai")
    fun clearData()
}