package ca.angelinmuwindsor.streamflix;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateUserActivity extends AppCompatActivity {

    //reference to the firestore db
    private FirebaseAuth firebaseAuth;


    //back button in the create user activity
    private Button back_button_create;

    //will hold the email and password entered by the user
    private EditText emailEditText;
    private EditText passEditText;
    //password conformation
    private EditText passConfEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        firebaseAuth = FirebaseAuth.getInstance();

        back_button_create  = (Button) findViewById(R.id.back_button_create);
        emailEditText = findViewById(R.id.email_create);
        passEditText = findViewById(R.id.pass_create);
        passConfEditText = findViewById(R.id.pass_confirm);

        back_button_create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadPrevious();
            }
        });
    }

    public void loadPrevious(){
        finish();
    }

    //accesses fireStore db using reference above
    public void createNewUser(View v){
        String email = emailEditText.getText().toString();
        String pass = passEditText.getText().toString();
        String passConf = passConfEditText.getText().toString();

        if(checkPassword(pass, passConf) && checkEmpty(email, pass, passConf)){
            //using the reference to add a new user to a database
            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(CreateUserActivity.this, "Success! Try Logging in.", Toast.LENGTH_SHORT).show();
                    loadLogin();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateUserActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("CreateUserActivity", e.toString());
                }
            });
        }

    }

    //checks to see if the password and password
    //conformation fields taken from the EditTexts
    //match
    public boolean checkPassword(String pass1, String pass2){
        boolean result = false;

        if(pass1.equals(pass2)){
            result = true;
        }else{
            Toast.makeText(this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
        }

        return result;
    }


    //Checks for empty fields in the signup process
    //takes the values stored in the EditText's from
    //activity_create_user.
    public boolean checkEmpty(String email, String pass, String passConf){
        boolean result = false;

        if(email.isEmpty() || pass.isEmpty() || passConf.isEmpty()){
            Toast.makeText(this, "empty Fields", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

    public void loadLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
