package ca.angelinmuwindsor.streamflix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import io.firekast.Firekast;

public class Main extends AppCompatActivity {
    private Button watch_button;
    private Button login_button;
    private Button back_button;
    private Button create_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        watch_button = (Button) findViewById(R.id.watch_button);
        login_button = (Button) findViewById(R.id.login_button);
        back_button  = (Button) findViewById(R.id.back_button);
        create_button  = (Button) findViewById(R.id.create_button);

        watch_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadWatch();
            }
        });
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadLogin();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadPrevious();
            }
        });
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCreateUser();
            }
        });
    }

    public void loadWatch(){
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }

    public void loadLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void loadCreateUser(){
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    public void loadPrevious(){
        finish();
    }


}
