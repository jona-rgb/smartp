package com.example.passwort;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


/*
public class MainActivity<NavigationView> extends AppCompatActivity {

     Executor executor;
     BiometricPrompt biometricPrompt;
     BiometricPrompt.PromptInfo promptInfo;


    Button finger;
    Button submit;
    EditText pin;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit =findViewById(R.id.submit);
        pin = findViewById(R.id.pin);
        finger = findViewById(R.id.finger);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback(){
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onAuthenticationsError(int errorCode, @NonNull CharSequence errString){
                super.onAuthenticationError(errorCode,errString);
                Toast.makeText(MainActivity.this, errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Authentication succeed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
            }
        });

        promtInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometrische Authentifikation").setSubtitle("Login using Fingerprint")
                .setNegativeButtonText("User App Passwort").build();

        finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promtInfo);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pin.getText().toString().equals("1705")) {
                    openActivity2();
                }
            }
        });
    }


    public void openActivity2() {
        Intent intent = new Intent(this, Activity_2.class);
        startActivity(intent);
    }
} */



public class MainActivity extends AppCompatActivity {

    ImageButton finger;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = null;
        finger = findViewById(R.id.finger);

        finger.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                authenticateUser(v);
            }
        });

        checkBiometricSupport();
        authenticateUser(view);
    }

    private void checkBiometricSupport() {

        KeyguardManager keyguardManager =
                (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        PackageManager packageManager = this.getPackageManager();

        assert keyguardManager != null;
        if (!keyguardManager.isKeyguardSecure()) {
            notifyUser("Lock screen security not enabled in Settings");
            return;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {

            notifyUser("Fingerprint authentication permission not enabled");
            return;
        }

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
        }

    }

    private void notifyUser(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private BiometricPrompt.AuthenticationCallback getAuthenticationCallback() {

        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              CharSequence errString) {
                notifyUser("Authentifikations error: " + errString);
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode,
                                             CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }

            @Override
            public void onAuthenticationSucceeded(
                    BiometricPrompt.AuthenticationResult result) {
                notifyUser("Authentifikation erfolgreich!");
                super.onAuthenticationSucceeded(result);
                openActivity2();
            }
        };
    }


    private CancellationSignal cancellationSignal;


    private CancellationSignal getCancellationSignal() {

        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new
                                                       CancellationSignal.OnCancelListener() {
                                                           @Override
                                                           public void onCancel() {
                                                               notifyUser("Cancelled via signal");
                                                           }
                                                       });
        return cancellationSignal;
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public void authenticateUser(View view) {
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("SafeP")
                .setSubtitle("Biometrische Authentifikation erforderlich!")
                .setDescription("Diese App ist durch biometrische Authentifikation geschützt")
                .setNegativeButton("Abbrechen", this.getMainExecutor(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notifyUser("Authentifikation abgebrochen");
                            }
                        })
                .build();

        biometricPrompt.authenticate(getCancellationSignal(), getMainExecutor(),
                getAuthenticationCallback());
    }

    public void openActivity2() {
        Intent intent = new Intent(this, Activity_2.class);
        startActivity(intent);
    }

}
