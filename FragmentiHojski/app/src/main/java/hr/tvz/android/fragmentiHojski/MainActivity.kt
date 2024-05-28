package hr.tvz.android.fragmentiHojski

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.channels.Channel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: List<Grafe>
    lateinit var titleList: Array<String>
    lateinit var detailsList: Array<String>
    lateinit var database: DatabaseReference
    private val brodo = AirModeReciever()
    lateinit var myAdapter: CustomAdapter
    var Kanal = "MojKanal"
    lateinit var db: GrafeDatabase
    lateinit var adapter: CustomAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(brodo, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        setContentView(R.layout.activity_main)

        println("Activitits")


        db = Room.databaseBuilder(
            applicationContext,
            GrafeDatabase::class.java, "Baza Grafi"
        ).fallbackToDestructiveMigration()
            .build()

        recyclerView = findViewById(R.id.myList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = CustomAdapter()
        recyclerView.adapter = adapter



        titleList = resources.getStringArray(R.array.nazivi)
        detailsList = resources.getStringArray(R.array.opisi)


        GlobalScope.launch(Dispatchers.IO) {
            dataList = db.dao.getGrafe()
            // Switch back to the main thread to update UI
            launch(Dispatchers.Main) {
                if (dataList.isEmpty()) {
                    getData()
                }
            }
        }



        GlobalScope.launch(Dispatchers.IO) {
            dataList = db.dao.getGrafe()
            // Switch back to the main thread to update UI
            launch(Dispatchers.Main) {
                adapter.setData(dataList)
            }
        }

        adapter.onItemClick = {

            val fragam = DetailFragment()
            val bundle = Bundle()

            val cFrag = supportFragmentManager.findFragmentByTag("fragTag") as DetailFragment?
            println(cFrag)
            bundle.putString("text", it.text)
            bundle.putString("desc", it.desc)
            bundle.putInt("img", it.image)
            fragam.arguments = bundle
            if (cFrag==null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentID, fragam, "fragTag").commit()
            }
            else {
                cFrag.setNaziv(it.text, it.desc, it.image)
            }


        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Kanal, "Ime Kanala", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Kaj ima?"
            val notManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notManager.createNotificationChannel(channel)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("a", "fail", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d("Token", token)
        })

    }


    val imageNvidia = R.drawable.nvidia
    val imageAMD = R.drawable.featured_amd


    private fun getData() {
        database = FirebaseDatabase.getInstance().getReference("grafe")
        database.get().addOnSuccessListener {
            if (it.exists()) {
                for (i in it.child("naziv").children) {
                    val naziv = i.value
                    val opis = it.child("opisi/${i.key}").value
                    /*val dataclass = itemViewModel(imageNvidia, naziv.toString(), opis.toString())*/
                    val grafa = Grafe(0, naziv.toString(), opis.toString(), imageNvidia)
                    GlobalScope.launch(Dispatchers.IO) {
                        db.dao.insertGrafu(grafa)
                    }
                    /*dataList.add(dataclass)*/
                }
                /*recyclerView.adapter = CustomAdapter(dataList)*/
            }
        }
        /*
        for (i in titleList.indices) {
            if (i<9) {
                val dataClass = itemViewModel(imageNvidia, titleList[i], detailsList[i])
                dataList.add(dataClass)
            }
            else {
                val dataClass = itemViewModel(imageAMD, titleList[i], detailsList[i])
                dataList.add(dataClass)
            }

        }
        recyclerView.adapter = CustomAdapter(dataList)*/
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(brodo)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

}