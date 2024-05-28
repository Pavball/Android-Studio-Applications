package hr.tvz.android.fragmentiHojski

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface GrafeDao {


    @Upsert
    suspend fun insertGrafu(grafe: Grafe)

    @Delete
    suspend fun deleteGrafu(grafe: Grafe)

    @Query("SELECT * FROM grafe ORDER BY text DESC")
    fun getGrafe(): List<Grafe>

    @Query("DELETE FROM grafe")
    fun nukeTable()
}
