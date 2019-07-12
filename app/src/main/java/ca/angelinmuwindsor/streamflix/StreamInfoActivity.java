package ca.angelinmuwindsor.streamflix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import io.firekast.Firekast;

public class StreamInfoActivity extends AppCompatActivity {
    //firebase database references
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference streamRef = db.collection("LiveStreams");


    private Button back_button;
    private Button broadcast_button;

    private EditText streamInfoDescription;
    private EditText streamInfoTitle;

    private double lat;
    private double lon;
    private GeoPoint geo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_info);

        Intent intent = getIntent();
        lat = intent.getExtras().getDouble("lat");
        lon = intent.getExtras().getDouble("lon");

        geo = new GeoPoint(lat, lon);

        back_button  = (Button) findViewById(R.id.back_button_info);
        broadcast_button  = (Button) findViewById(R.id.broadcast_button_info);

        streamInfoTitle = findViewById(R.id.stream_info_title);
        streamInfoDescription = findViewById(R.id.stream_info_description);

        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadPrevious();
            }
        });
        broadcast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                broadcastStream();
            }
        });
    }

    public void loadPrevious(){
        finish();
    }

    public void broadcastStream(){
        String streamTitle = streamInfoTitle.getText().toString();
        String streamDescription = streamInfoDescription.getText().toString();
        StreamInfo newStream = new StreamInfo(streamTitle, streamDescription);
        newStream.setStreamLocation(geo);
        if(streamTitle.trim().isEmpty() || streamDescription.trim().isEmpty()){
            Toast.makeText(this, "Please fill in the fields", Toast.LENGTH_SHORT);
            return;
        }

        String path = streamRef.document().getId();
        streamRef.document(path).set(newStream);


        Intent intent = new Intent(StreamInfoActivity.this, MainActivity2.class);
        intent.putExtra("path",path);
        startActivity(intent);
        //Intent intent = new Intent(this, MainActivity2.class);
        //startActivity(intent);
    }

}