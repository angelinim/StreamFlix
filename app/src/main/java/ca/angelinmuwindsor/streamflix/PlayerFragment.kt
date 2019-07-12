package ca.angelinmuwindsor.streamflix

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.firestore.FirebaseFirestore
import io.firekast.FKError
import io.firekast.FKPlayerView
import io.firekast.FKStream
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.fragment_streamer.*

class PlayerFragment : Fragment(), FKPlayerView.Callback {



    private var path: String = ""
    private val db = FirebaseFirestore.getInstance()
    private val streamRef = db.collection("LiveStreams")
    private var STREAMID_KEY: String = "streamId"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        videoView.setPlayerListener(this)
        //videoView.exoPlayerView.useController = false


        startStream()
    }

    private fun startStream() {
        path = (activity as PlayCurrentStream).getStreamId()
        //Toast.makeText(this.context,path,Toast.LENGTH_SHORT).show()
        streamRef.document(path).get()
                .addOnSuccessListener {
                    if(it.exists()){
                        var currentStreamId = it.getString(STREAMID_KEY)!!

                        if (currentStreamId.isEmpty()) {
                            return@addOnSuccessListener
                        }
                        //Toast.makeText(this.context,currentStreamId,Toast.LENGTH_SHORT).show()
                        videoView.play(currentStreamId.toString())
                    }
                    //Toast.makeText(this,currentStreamId,Toast.LENGTH_SHORT).show()
                }
    }

    override fun onPlayerWillPlay(stream: FKStream?, error: FKError?) {

        if (error != null) {
            Toast.makeText(this.context, "Error: $error", Toast.LENGTH_SHORT).show()
            return
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        videoView.stop()
    }


}