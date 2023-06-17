package org.d3if4130.hitungnilaiakhir.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NilaiEntity::class], version = 1, exportSchema = false)
abstract class Nilai : RoomDatabase() {
    abstract val dao: NilaiDao
    companion object {
        @Volatile
        private var INSTANCE: Nilai? = null
        fun getInstance(context: Context): Nilai{
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Nilai::class.java,
                        "bmi.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}