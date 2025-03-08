package com.example.fraudcalldetection;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FraudResultActivity extends AppCompatActivity {

    private TextView riskLevelText, transcriptionText, deepfakeResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraud_result); // Ensure XML file name matches!

        // Link Java variables to XML TextViews
        riskLevelText = findViewById(R.id.riskLevelText);
        transcriptionText = findViewById(R.id.transcriptionText);
        deepfakeResultText = findViewById(R.id.deepfakeResultText);

        // Get data from Intent
        String riskLevel = getIntent().getStringExtra("RISK_LEVEL");
        String transcription = getIntent().getStringExtra("TRANSCRIPTION");
        String deepfakeResult = getIntent().getStringExtra("DEEPFAKE_RESULT");

        // Set values
        if (riskLevel != null) riskLevelText.setText("Risk Level: " + riskLevel);
        if (transcription != null) transcriptionText.setText("Transcription: " + transcription);
        if (deepfakeResult != null) deepfakeResultText.setText("Deepfake Detection: " + deepfakeResult);
    }
}
