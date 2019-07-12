package ca.angelinmuwindsor.streamflix

import android.os.Bundle
//import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity2 : AppCompatActivity() {

    private var path: String? = null

    private val db = FirebaseFirestore.getInstance()
    private val streamRef = db.collection("LiveStreams")
    private var STREAMID_KEY = "streamId"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val intent = intent
        path = intent.getStringExtra("path")


        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, StreamerFragment())
                .commit()

    }

    fun setStreamId(p0:String){
        val lll = path
        if(lll!=null){
            streamRef.document(lll).update(STREAMID_KEY, p0)
        }
    }

}
