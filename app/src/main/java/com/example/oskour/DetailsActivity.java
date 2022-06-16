package com.example.oskour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsActivity extends AppCompatActivity {
    TextView appName, appId, appPassword;
    FloatingActionButton upgrade, delete, retour;
    CheckBox checkbox;
    String id_app, name, image, userId, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);
        appName = findViewById(R.id.upgradeAppName);
        appId = findViewById(R.id.upgradeId);
        appPassword = findViewById(R.id.upgradePassword);
        upgrade = findViewById(R.id.upgradeApp);
        delete = findViewById(R.id.deleteApp);
        retour = findViewById(R.id.retour);
        checkbox = findViewById(R.id.btn_checkbox);
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                appPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                appPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        getAndSetIntentData();
        upgrade.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, UpdateActivity.class);
            intent.putExtra("id_app",id_app);
            intent.putExtra("app_name",name);
            intent.putExtra("app_image",image);
            intent.putExtra("user_id",userId);
            intent.putExtra("user_password",userPassword);
            startActivityForResult(intent,1);
        });
        delete.setOnClickListener(v -> {
            Log.i("voici", "nom app " + appName);
            confirmDialog();
        });

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
            DataBaseHelper db = new DataBaseHelper(DetailsActivity.this);
            db.deleteOneRow(id_app);
            finish();
        });
        builder.setNegativeButton("Non", (dialog, i) -> {
        });
        builder.create().show();
    }
}
