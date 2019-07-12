package ca.angelinmuwindsor.streamflix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GroupsActivity extends AppCompatActivity {
    //firebase database references
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference streamRef = db.collection("LiveStreams");

    private StreamAdapter adapter;

    private Button back_button_groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        createRecyclerView();

        //button controllers
        back_button_groups  = (Button) findViewById(R.id.back_button_groups);
        back_button_groups.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadPrevious();
            }
        });
    }

    public void loadPrevious(){
        finish();
    }

    public void createRecyclerView(){
        Query query = streamRef.orderBy("streamTitle", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<StreamInfo> options = new FirestoreRecyclerOptions.Builder<StreamInfo>()
                .setQuery(query, StreamInfo.class)
                .build();

        //making an adapter for the recycler view
        adapter = new StreamAdapter(options);

        //loading our recycler view
        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        //this is what allows us to click on items in the recycler view
        adapter.setOnItemClickListener(new StreamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //these lines will get the information about the stream that was clicked
                //in the recycler . id will be necessary to send the DOCUMENT id from
                //the firebase database to the PlayerActivity
                //StreamInfo stream = documentSnapshot.toObject(StreamInfo.class);
                //documentSnapshot is in the StreamAdaptor class
                String id = documentSnapshot.getId();
                Intent intent = new Intent(GroupsActivity.this, PlayCurrentStream.class);
                intent.putExtra("path",id);
                startActivity(intent);


            }
        });
    }

    //used to allow the recyclerView to listen for
    //changes in the database
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    //stops the recyclerView from listening
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
