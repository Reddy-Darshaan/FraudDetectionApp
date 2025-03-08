package com.example.fraudcalldetection;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;

public class CallRecordingService extends Service {
    private MediaRecorder recorder;
    private boolean isRecording = false;
    private File audioFile;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    private void startRecording() {
        try {
            String fileName = "call_" + System.currentTimeMillis() + ".wav";
            audioFile = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName);

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(audioFile.getAbsolutePath());

            recorder.prepare();
            recorder.start();
            isRecording = true;

            Toast.makeText(this, "📞 Call Recording Started!", Toast.LENGTH_SHORT).show();
            Log.d("CallRecordingService", "Recording started: " + audioFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("CallRecordingService", "Error starting recording", e);
            Toast.makeText(this, "❌ Error Starting Recording", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        if (recorder != null && isRecording) {
            recorder.stop();
            recorder.release();
            isRecording = false;

            Toast.makeText(this, "✅ Call Recording Stopped!", Toast.LENGTH_SHORT).show();
            Log.d("CallRecordingService", "Recording saved: " + audioFile.getAbsolutePath());
        }
        super.onDestroy();
    }
}

