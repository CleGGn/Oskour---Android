package com.example.oskour;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText appName, appId, appPassword ;
    Button addApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new);

        appName = findViewById(R.id.addAppName);
        appId = findViewById(R.id.addId);
        appPassword = findViewById(R.id.addPassword);
        addApp = findViewById(R.id.addApp);

        addApp.setOnClickListener(v -> {
            DataBaseHelper myDB = new DataBaseHelper(AddActivity.this);
            myDB.addApp(appName.getText().toString().trim(),
                    appId.getText().toString().trim(),
                    appPassword.getText().toString().trim());
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        Button retour = findViewById(R.id.retour);
        retour.setOnClickListener(v -> { // Fonction qui quitte l'application
            finish();
        });
    }
}
