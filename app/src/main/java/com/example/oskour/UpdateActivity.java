package com.example.oskour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText appName, appId, appPassword;
    Button upgrade, delete;

    String id_app, name, image, userId, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update);

        appName = findViewById(R.id.upgradeAppName);
        appId = findViewById(R.id.upgradeId);
        appPassword = findViewById(R.id.upgradePassword);
        upgrade = findViewById(R.id.upgradeApp);
        delete = findViewById(R.id.deleteApp);


        getAndSetIntentData();

        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper db = new DataBaseHelper(UpdateActivity.this);
                name = appName.getText().toString().trim();
                userId = appId.getText().toString().trim();
                userPassword = appPassword.getText().toString().trim();
                db.updateData(id_app, name, userId, userPassword);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("voici", "nom app " + appName);
                confirmDialog();
            }
        });


        Button retour = findViewById(R.id.retour);
        retour.setOnClickListener(v -> { // Fonction qui quitte l'application
            finish();
        });

    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id_app")
                && getIntent().hasExtra("app_name")
                && getIntent().hasExtra("app_image")
                && getIntent().hasExtra("user_id")
                && getIntent().hasExtra("user_password")){

            id_app = getIntent().getStringExtra("id_app");
            name = getIntent().getStringExtra("app_name");
            image = getIntent().getStringExtra("app_image");
            userId = getIntent().getStringExtra("user_id");
            userPassword = getIntent().getStringExtra("user_password");

            appName.setText(name);
            appId.setText(userId);
            appPassword.setText(userPassword);
        } else {
            Toast.makeText(this,"Pas de données",Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Supprimer " + name + " ?");
        builder.setMessage("Êtes-vous sur de vouloir supprimer " + name + " ?");
        builder.setPositiveButton("Oui", (dialog, i) -> {
            DataBaseHelper db = new DataBaseHelper(UpdateActivity.this);
            db.deleteOneRow(id_app);
            finish();
        });
        builder.setNegativeButton("Non", (dialog, i) -> {
        });
        builder.create().show();
    }

}