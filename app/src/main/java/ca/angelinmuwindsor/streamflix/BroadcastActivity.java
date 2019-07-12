package ca.angelinmuwindsor.streamflix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

public class BroadcastActivity extends AppCompatActivity {

    private static final String TAG = "BroadcastActivity";

    private String path;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference streamRef = db.collection("LiveStreams");
    private static final String STREAMID_KEY = "streamId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_streamer);

        Intent intent = getIntent();
        path = intent.getExtras().getString("path");



        //myFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //init contains the onClick which runs necessary methods to get location
        //and put relevent path data into the firebase database
       //if(isServiceOK()){
            init();
        //}
    }

    // Map Button and transitioning to Google Map
    //ONCLICK will only work if service is OK ^
    private void init(){
        Button mapButton = findViewById(R.id.broadcast_button_broadcast);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getLocationPermission();
//                getMyLocation();
                updateLocation();
            }
        });
    }

    private void updateLocation(){
        Toast.makeText(BroadcastActivity.this,path,Toast.LENGTH_SHORT).show();
        //now you have the path for the file in the database
        //in order to update the streamID you need to call
        //streamRef.document(path).update(STREAMID_KEY, "hello");

    }







//---------------------------------------------------------------------------------------GOOGLE MAPS
//    public boolean isServiceOK(){ // Checking Google Service
//        Log.d(TAG, "isServicesOK: Checking google services version");
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(BroadcastActivity.this);
//
//        if (available == ConnectionResult.SUCCESS){
//            //everything oK
//            Log.d(TAG, "isServicesOK: Everything is good");
//            return true;
//        }
//        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //we can resolve
//            Log.d(TAG, "isServicesOK: We need a fix");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(BroadcastActivity.this, available, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        }
//        else{
//            Toast.makeText(this, "You cannot make map requests", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }

//    private void getLocationPermission() { // Checking if application has permission to check for coarse and fine locations
//        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                locationPermissionGranted = true;
//
//            } else {
//                ActivityCompat.requestPermissions(this, permission, LocationPermissionCode);
//            }
//        } else {
//            ActivityCompat.requestPermissions(this, permission, LocationPermissionCode);
//        }
//    }

    //Locating where user is
    //the information can be stored as a String, LatLng object, or as numbers
//    private void getMyLocation(){
//        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        try{
//            if(locationPermissionGranted){
//                Task location = myFusedLocationClient.getLastLocation();
//                ((Task) location).addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if(task.isSuccessful()){
//                            Log.d(TAG,"Found Location");
//                            Location currentLocation = (Location) task.getResult();
//
//                            //saves the location in a GeoPoint variable
//                            //LatLonPoint extends GeoPoint
//                            streamLocation = new LatLonPoint(currentLocation.getLatitude(),
//                                    currentLocation.getLongitude());
//
//                            Toast.makeText(BroadcastActivity.this,streamLocation.toString(),Toast.LENGTH_SHORT).show();
//                        }else{
//                            Log.d(TAG,"Location is Null");
//                            Toast.makeText(BroadcastActivity.this,"Location Not Found",Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//            }
//        }catch(SecurityException e){
//            Log.e(TAG,"getMyLocation: SecurityException: "+ e.getMessage());
//        }
//    }

    //ease of use class that takes a float and converts it
    //to a 6 digit int so it can be put into a GeoPoint
    //variable
//    private static final class LatLonPoint extends GeoPoint {
//        public LatLonPoint(double latitude, double longitude) {
//            super((int) (latitude * 1E6), (int) (longitude * 1E6));
//        }
//    }



}
