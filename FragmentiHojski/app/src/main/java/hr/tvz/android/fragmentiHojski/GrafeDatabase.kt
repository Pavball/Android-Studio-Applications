package hr.tvz.android.fragmentiHojski

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Grafe::class],
    version = 2
)
abstract class GrafeDatabase: RoomDatabase() {

    abstract val dao: GrafeDao
}