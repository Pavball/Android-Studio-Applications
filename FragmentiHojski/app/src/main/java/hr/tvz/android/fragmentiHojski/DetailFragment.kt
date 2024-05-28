package hr.tvz.android.fragmentiHojski

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import android.annotation.SuppressLint
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "text"
private const val ARG_PARAM2 = "desc"
private const val ARG_PARAM3 = "img"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: Int? = null
    lateinit var name: TextView
    lateinit var desc: TextView
    lateinit var img: ImageButton
    lateinit var rootView: View


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getInt(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_detail, container, false)
        name = rootView.findViewById<View>(R.id.gpuName) as TextView
        desc = rootView.findViewById<TextView>(R.id.desc) as TextView
        img = rootView.findViewById<ImageButton>(R.id.imid)
        name.text = param1.toString()
        desc.text = param2.toString()
        img.setImageResource(param3!!)
        return rootView
    }

    fun setNaziv(neam: String?, disc: String?, imag: Int?) {
        name!!.text = neam
        desc!!.text = disc
        img.setImageResource(imag!!)
    }

    fun getToImage(view: View) {
        val img = rootView.findViewById<ImageButton>(R.id.imid)
        val intent = Intent(view.context, ImageActivity::class.java)
        intent.putExtra("image", R.drawable.nvidia)

        view.context.startActivity(intent)
    }

    private fun shareContent() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Your shared text here")
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    fun openPage(view: View) {
        val url:String = resources.getString(R.string.links)
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}
