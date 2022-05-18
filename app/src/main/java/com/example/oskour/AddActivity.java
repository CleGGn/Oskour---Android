package com.example.oskour;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    Button addApp;
    TextInputEditText appPassword, appName, appId;


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
            myDB.addApp(Objects.requireNonNull(appName.getText()).toString().trim(),
                    Objects.requireNonNull(appId.getText()).toString().trim(),
                    Objects.requireNonNull(appPassword.getText()).toString().trim());
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
