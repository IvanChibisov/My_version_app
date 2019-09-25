package com.example.my_version_app;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.os.Bundle;
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showVersion();
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
