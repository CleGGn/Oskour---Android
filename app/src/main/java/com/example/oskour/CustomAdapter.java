package com.example.oskour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<String> id;
    private final ArrayList<String> app_name;
    private final ArrayList<String> app_image;
    private final ArrayList<String> user_id;
    private final ArrayList<String> user_password;
    Activity activity;

    CustomAdapter(Activity activity,
                  Context context,
                  ArrayList<String> id,
                  ArrayList<String> app_name,
                  ArrayList<String> app_image,
                  ArrayList<String> user_id,
                  ArrayList<String> user_password){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.app_name = app_name;
        this.app_image = app_image;
        this.user_id = user_id;
        this.user_password = user_password;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
    holder.app_name_txt.setText(String.valueOf(app_name.get(position)));
    holder.mainLayout.setOnClickListener(v -> {
    Intent intent = new Intent(context, DetailsActivity.class);
    intent.putExtra("id_app",String.valueOf(id.get(position)));
        intent.putExtra("app_name",String.valueOf(app_name.get(position)));
        intent.putExtra("app_image",String.valueOf(app_image.get(position)));
        intent.putExtra("user_id",String.valueOf(user_id.get(position)));
        intent.putExtra("user_password",String.valueOf(user_password.get(position)));
    activity.startActivityForResult(intent, 1);

    });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView id_txt, app_name_txt, app_image_txt, user_id_txt, user_password_txt;
    LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            app_name_txt = itemView.findViewById(R.id.app_name);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
