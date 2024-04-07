package com.jlib.miscacharros;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.jlib.miscacharros.R;
import com.jlib.miscacharros.ui.cacharro.VistaDetalleCacharroActivity;

public class Notificaciones extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        int id = intent.getIntExtra("id",0);
        String uid = intent.getStringExtra("uid");

        Intent activityIntent = new Intent(context, VistaDetalleCacharroActivity.class);
        activityIntent.putExtra("id",id);
        activityIntent.putExtra("modo",2);
        activityIntent.putExtra("pos",0);
        activityIntent.putExtra("uid",uid);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, activityIntent, PendingIntent.FLAG_IMMUTABLE);

        // Crear una notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.miscaharros)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());

    }

    public static boolean checkNotificationPermissions(Context context) {
        // Check if notification permissions are granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            boolean isEnabled = notificationManager.areNotificationsEnabled();

            if (!isEnabled) {
                // Open the app notification settings if notifications are not enabled
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                context.startActivity(intent);

                return false;
            }
        } else {
            boolean areEnabled = NotificationManagerCompat.from(context).areNotificationsEnabled();
            if (!areEnabled) {
                // Open the app notification settings if notifications are not enabled
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                context.startActivity(intent);
                return false;
            }
        }
        // Permissions are granted
        return true;
    }
}
