package ca.angelinmuwindsor.streamflix

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils.replace
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class PlayCurrentStream : AppCompatActivity() {

    private var path: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        path = intent.getStringExtra("path")


        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, PlayerFragment())
                .commit()

    }

    fun getStreamId(): String{
        //Toast.makeText(this,"Current stream ID: "+currentStreamId,Toast.LENGTH_SHORT).show()
        return path
    }





}
