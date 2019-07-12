package ca.angelinmuwindsor.streamflix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PlayerActivity extends AppCompatActivity {
    private static final String KEY_TITLE = "streamTitle";
    private static final String KEY_ID = "streamId";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference streamRef = db.collection("LiveStreams");

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //this bit of code RECEIVES the id of the stream DOCUMENT (ex. 7pcVGUFdDEpzhL90EvYV)
        //and saves it in the new streamId string.
        //the stream DOCUMENT is in the firebase database
        Intent intent = getIntent();
        path = intent.getExtras().getString("path");

        streamRef.document(path).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String title = documentSnapshot.getString(KEY_TITLE);
                    Toast.makeText(PlayerActivity.this,path + " " + title,Toast.LENGTH_SHORT).show();
                }
            }
        });


        //mVideoView.setPlayerListener(new MyFKPlayerCallback());
        //mPlayerView.play("THE_STREAM_ID");
    }
}
