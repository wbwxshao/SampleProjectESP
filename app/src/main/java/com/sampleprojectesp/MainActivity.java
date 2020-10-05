package com.sampleprojectesp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button writeButton = (Button) findViewById(R.id.write);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EncryptedSharedPreference.putString(getApplicationContext(), "key", "1234");
                Log.d("MainActivity", "key has been stored");
            }
        });

        Button readButton = (Button) findViewById(R.id.read);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("MainActivity","Stored key is " + EncryptedSharedPreference.getString(getApplicationContext(), "key"));
                } catch (Exception e) {
                    Log.e("MainActivity", "Cannot read key " + e.getMessage());
                }
            }
        });
    }


}