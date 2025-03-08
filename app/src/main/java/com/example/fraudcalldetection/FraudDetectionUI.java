package com.example.fraudcalldetection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FraudDetectionUI extends AppCompatActivity {

    private TextView riskLevelText;
    private TextView transcriptionText;
    private TextView deepfakeResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraud_detection); // Ensure this file exists

        // Link Java variables to XML elements
        riskLevelText = findViewById(R.id.riskLevelText);
        transcriptionText = findViewById(R.id.transcriptionText);
        deepfakeResultText = findViewById(R.id.deepfakeResultText);

        // Get data from Intent safely
        Intent intent = getIntent();
        String riskLevel = intent.getStringExtra("RISK_LEVEL");
        String transcription = intent.getStringExtra("TRANSCRIPTION");
        String deepfakeResult = intent.getStringExtra("DEEPFAKE_RESULT");

        // Set text only if values are not null
        if (riskLevel != null) riskLevelText.setText("Risk Level: " + riskLevel);
        if (transcription != null) transcriptionText.setText("Transcription: " + transcription);
        if (deepfakeResult != null) deepfakeResultText.setText("Deepfake Analysis: " + deepfakeResult);
    }
}
