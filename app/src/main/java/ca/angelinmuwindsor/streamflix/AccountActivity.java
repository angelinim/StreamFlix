package ca.angelinmuwindsor.streamflix;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private FusedLocationProviderClient myFusedLocationClient;
    private boolean locationPermissionGranted = false;
    private static final int LocationPermissionCode = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermission();

        if(isServiceOK()){
            init();
        }
    }


    public void loadStreams(View view) {
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        Toast.makeText(this, "Logged Out.",Toast.LENGTH_SHORT);
    }

    private void getLocationPermission() { // Checking if application has permission to check for coarse and fine locations
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;

            } else {
                ActivityCompat.requestPermissions(this, permission, LocationPermissionCode);
            }
        } else {
            ActivityCompat.requestPermissions(this, permission, LocationPermissionCode);
        }
    }

    public boolean isServiceOK(){ // Checking Google Service
        Log.d(TAG, "isServicesOK: Checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AccountActivity.this);

        if (available == ConnectionResult.SUCCESS){
            //everything oK
            Log.d(TAG, "isServicesOK: Everything is good");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //we can resolve
            Log.d(TAG, "isServicesOK: We need a fix");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(AccountActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You cannot make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void init(){
        Button findMe = findViewById(R.id.broadcast_button_account);
        findMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
            }
        });
    }

    private void getMyLocation(){ //Locating where I am. Does not store information anywhere
        //But the information can be stored as a String, LatLng object, or as numbers
        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(locationPermissionGranted){
                Task location = myFusedLocationClient.getLastLocation();
                ((Task) location).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"Found Location");
                            Location currentLocation = (Location) task.getResult();

                            LatLng sendLoc = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                            Intent intent = new Intent(AccountActivity.this, StreamInfoActivity.class);
                            intent.putExtra("lat",sendLoc.latitude);
                            intent.putExtra("lon",sendLoc.longitude);
                            startActivity(intent);
                        }else{
                            Log.d(TAG,"Location is Null");
                            Toast.makeText(AccountActivity.this,"Location Not Found",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }catch(SecurityException e){
            Log.e(TAG,"getMyLocation: SecurityException: "+ e.getMessage());
        }
    }
}
