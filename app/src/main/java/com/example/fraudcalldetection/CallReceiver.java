package com.example.fraudcalldetection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                Log.d("CallReceiver", "Call answered - Start recording");
                // Start recording when the call is answered
                context.startService(new Intent(context, CallRecordingService.class));
            }
            else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                Log.d("CallReceiver", "Call ended - Stop recording");
                // Stop recording when the call ends
                context.stopService(new Intent(context, CallRecordingService.class));
            }
        }
    }
}
