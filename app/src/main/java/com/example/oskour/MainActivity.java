package com.example.oskour;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity"; // Le TAG pour les Log
    DataBaseHelper db;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ArrayList<String>  id, app_name, app_image, user_id, user_password;

    FloatingActionButton exit;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list_app_view);
        db = new DataBaseHelper(MainActivity.this);
        id = new ArrayList<>();
        app_name = new ArrayList<>();
        app_image = new ArrayList<>();
        user_id = new ArrayList<>();
        user_password = new ArrayList<>();

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(v -> { // Fonction qui quitte l'application
            Log.i(TAG, "clicExit");
            finish();
            System.exit(0);
        });

        add = findViewById(R.id.btn_add);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddActivity.class);
            v.getContext().startActivity(intent);
        });

        storeDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this, this, id, app_name, app_image, user_id, user_password);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "Pas de données", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                app_name.add(cursor.getString(1));
                app_image.add(cursor.getString(2));
                user_id.add(cursor.getString(3));
                user_password.add(cursor.getString(4));
            }
        }
    }
}