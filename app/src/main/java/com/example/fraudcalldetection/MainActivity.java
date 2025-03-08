package com.example.fraudcalldetection;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 1001;
    private CallRecorder callRecorder;
    private Button startRecordingButton, stopRecordingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startRecordingButton = findViewById(R.id.startRecordingButton);
        stopRecordingButton = findViewById(R.id.stopRecordingButton);
        callRecorder = new CallRecorder();

        // Request Permissions on App Launch
        if (!checkPermissions()) {
            requestPermissions();
        }

        startRecordingButton.setOnClickListener(view -> {
            if (checkPermissions()) {
                callRecorder.startRecording(MainActivity.this, "unknown_number");
                startRecordingButton.setVisibility(View.GONE);
                stopRecordingButton.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(MainActivity.this, "Grant permissions first!", Toast.LENGTH_SHORT).show();
                requestPermissions();
            }
        });

        stopRecordingButton.setOnClickListener(view -> {
            callRecorder.stopRecording(MainActivity.this);
            startRecordingButton.setVisibility(View.VISIBLE);
            stopRecordingButton.setVisibility(View.GONE);
        });
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "✅ Permissions granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "❌ Permissions denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
