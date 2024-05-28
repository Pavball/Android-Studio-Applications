package hr.tvz.android.fragmentiHojski

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class itemViewModel(val image: Int, val text: String, val desc: String) : Parcelable {
}