package com.example.oskour;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecurityActivity extends AppCompatActivity {

    public TextView msg_security;
    public Button btn_security;
    public BiometricPrompt prompt;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_security);

        msg_security = findViewById(R.id.security_msg);
        btn_security = findViewById(R.id.security_btn);


        BiometricManager manager = BiometricManager.from(this);

        switch (manager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
                msg_security.setText("Vous pouvez utiliser la biométrie.");
                break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msg_security.setText("Cet appareil ne possède pas de validation par biométrie");
                btn_security.setVisibility(View.GONE);
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msg_security.setText("Cet appareil ne possède pas d'empreinte digitale enregistrée, veuillez vérifier vos options de sécurité.");
                btn_security.setVisibility(View.GONE);
                break;
        }

        prompt = new BiometricPrompt(this,
                ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Identification réussie.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo info = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric")
                .setNegativeButtonText("Cancel")
                .build();

        btn_security.setOnClickListener(v -> prompt.authenticate(info));

    }
}