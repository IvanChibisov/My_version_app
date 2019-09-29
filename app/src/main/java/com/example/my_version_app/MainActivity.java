package com.example.my_version_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.os.Bundle;
import android.content.pm.PackageManager;


public class MainActivity extends AppCompatActivity {
    private final static String PERMISSION = Manifest.permission.READ_PHONE_STATE;
    private final static int PERMISSION_CODE = 1;
    private static int REQUESTCANCELED = 0;
    private void showId(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showVersion();
        showID();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            showId();

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        showID();
    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    private void dialogForPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This app needs phones ID, get the permission")
                .setTitle("Request the permission")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        REQUESTCANCELED = 0;
                        requestPermission(PERMISSION, PERMISSION_CODE);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        REQUESTCANCELED += 1;
                        showID();
                    }
                });
        builder.create().show();
    }

    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You don`t have permission. Do it request again?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogForPermission();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showID();
                    }
                });
        builder.create().show();
    }

    private void showID() {
        if (REQUESTCANCELED != 0){
            showMessage();
        }
        else {
            if (ContextCompat.checkSelfPermission(this, PERMISSION)
                    != PackageManager.PERMISSION_GRANTED) {
                dialogForPermission();
            }
            else {
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String phoneID = telephonyManager.getDeviceId();
                TextView phoneID_TV = findViewById(R.id.phoneID_Tv);
                phoneID_TV.setText(phoneID);
            }
        }

    }

    private void showVersion(){
        String version;
        try{
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch(PackageManager.NameNotFoundException e){
            version = "unknown";
        }
        TextView versionTextView = findViewById(R.id.version_Tv);
        versionTextView.setText("Application version: " + version);
    }
}
