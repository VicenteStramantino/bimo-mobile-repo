package com.aula.appbimo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        //Receber notificação
        Toast.makeText(context, "Recebido", Toast.LENGTH_SHORT).show();
    }
}
