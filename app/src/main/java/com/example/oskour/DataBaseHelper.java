package com.example.oskour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "Oskour.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Oskourdatabase";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_APPNAME = "app_name";
    private static final String COLUMN_APPIMAGE = "app_image";
    private static final String COLUMN_USERID = "user_id";
    private static final String COLUMN_USERPASSWORD = "user_password";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_APPNAME + " TEXT, " +
                        COLUMN_APPIMAGE + " TEXT, " +
                        COLUMN_USERID + " TEXT, " +
                        COLUMN_USERPASSWORD + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addApp(String app, String id, String password ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_APPNAME, app);
        cv.put(COLUMN_USERID, id);
        cv.put(COLUMN_USERPASSWORD, password);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Echec de l'ajout", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Application ajoutée !", Toast.LENGTH_SHORT).show();
        }
    }

     Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
     }

    void updateData(String row_id, String appName, String userId, String userPassword){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_APPNAME, appName);
        cv.put(COLUMN_USERID, userId);
        cv.put(COLUMN_USERPASSWORD, userPassword);

        long result = db.update(TABLE_NAME, cv, "id= ?", new String[]{row_id});
        if (result == -1){
        Toast.makeText(context, "Echec !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Application mise à jour !", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id= ?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Echec !", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Application supprimée !", Toast.LENGTH_SHORT).show();
        }
    }
}
