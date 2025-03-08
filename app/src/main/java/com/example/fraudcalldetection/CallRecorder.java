package com.example.fraudcalldetection;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;

public class CallRecorder {
    private MediaRecorder recorder;
    private boolean isRecording = false;
    private File audioFile;

    public void startRecording(Context context, String phoneNumber) {
        if (isRecording) return;

        String fileName = "call_" + phoneNumber + "_" + System.currentTimeMillis() + ".3gp";
        audioFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(audioFile.getAbsolutePath());
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            isRecording = true;

            Toast.makeText(context, "📞 Call Recording Started!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "❌ Error Starting Recording", Toast.LENGTH_LONG).show();
        }
    }

    public void stopRecording(Context context) {
        if (recorder != null && isRecording) {
            recorder.stop();
            recorder.release();
            recorder = null;
            isRecording = false;

            Toast.makeText(context, "✅ Recording saved at: " + audioFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        }
    }
}
