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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private Button login_button_login;
    private Button back_button_login;

    private EditText emailEditText;
    private EditText passEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.editText2);
        passEditText = findViewById(R.id.editText4);

        login_button_login = (Button) findViewById(R.id.login_button_login);
        back_button_login  = (Button) findViewById(R.id.back_button_login);

        back_button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadPrevious();
            }
        });
        login_button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginUser();
            }
        });
    }

    public void loadPrevious(){
        finish();
    }

    public void loginUser(){
        String email = emailEditText.getText().toString();
        String pass = passEditText.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    loadAccount();
                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("CreateUserActivity", e.toString());
            }
        });
    }

    public void loadAccount(){
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}
