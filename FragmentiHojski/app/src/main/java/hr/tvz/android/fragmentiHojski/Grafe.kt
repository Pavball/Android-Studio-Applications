package hr.tvz.android.fragmentiHojski

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Grafe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val desc: String,
    val image: Int
)
