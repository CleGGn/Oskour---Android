package com.example.oskour;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class UpdateActivity extends AppCompatActivity {
    FloatingActionButton upgrade;
    TextInputEditText appPassword, appName, appId;

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

        getAndSetIntentData();

        upgrade.setOnClickListener(v -> {
            DataBaseHelper db = new DataBaseHelper(UpdateActivity.this);
            name = Objects.requireNonNull(appName.getText()).toString().trim();
            userId = Objects.requireNonNull(appId.getText()).toString().trim();
            userPassword = Objects.requireNonNull(appPassword.getText()).toString().trim();
            db.updateData(id_app, name, userId, userPassword);
            finish();
        });

        FloatingActionButton retour = findViewById(R.id.retour);
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
}