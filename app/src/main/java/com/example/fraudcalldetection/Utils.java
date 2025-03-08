package com.example.fraudcalldetection;

import android.media.MediaRecorder;
import java.io.File;

public class Utils {

    public static File getAudioFile() {
        return new File("/sdcard/recorded_audio.wav");
    }

    public static MediaRecorder setupRecorder(File file) {
        MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(file.getAbsolutePath());
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        return recorder;
    }
}
