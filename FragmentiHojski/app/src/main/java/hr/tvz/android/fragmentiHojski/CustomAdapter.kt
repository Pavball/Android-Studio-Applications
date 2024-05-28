package hr.tvz.android.fragmentiHojski

import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter() : RecyclerView.Adapter<CustomAdapter.ViewHolderClass>() {
    var onItemClick: ((Grafe) -> Unit) ?=null
    private var dataList: ArrayList<Grafe> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolderClass(itemView)
    }



    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val curItem = dataList[position]
        holder.rvImage.setImageResource(curItem.image)
        holder.rvText.text = curItem.text
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.cardView.context, R.anim.fade_in))


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            Toast.makeText(context, "Clicked on ${curItem.text}", Toast.LENGTH_SHORT).show()
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("image", curItem.image)
            intent.putExtra("name", curItem.text)
            intent.putExtra("desc", curItem.desc)
            println(context.resources.configuration.orientation)
            if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                it.context.startActivity(intent)
            else {
                onItemClick?.invoke(curItem)
            }
        }

    }
    fun setData(data: List<Grafe>) {
        dataList = data as ArrayList<Grafe>
        notifyDataSetChanged()
    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvImage:ImageView = itemView.findViewById(R.id.imageview)
        val rvText:TextView = itemView.findViewById(R.id.textView)
        val cardView: CardView = itemView.findViewById(R.id.cardview)

    }


}
