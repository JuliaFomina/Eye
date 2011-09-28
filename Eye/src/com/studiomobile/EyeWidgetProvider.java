package com.studiomobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.*;
import android.widget.RemoteViews;

import java.util.Calendar;


public class EyeWidgetProvider extends AppWidgetProvider {


    @Override
    public void onDisabled(Context context) {
        context.stopService(new Intent(context, UpdateService.class));
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent intent = new Intent(context, UpdateService.class);
        context.startService(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            /* code: on click widget change image */
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            Intent widgetIntent = new Intent(UpdateService.ACTION_ON_CLICK);
            PendingIntent pendingIntentViewClick = PendingIntent.getBroadcast(context, 0, widgetIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_imageview, pendingIntentViewClick);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

            /* alarmManager for change widget image day/night */
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(UpdateService.ACTION_CHANGE_DAY_NIGHT), 0);
            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 30000, pi);
        }
    }


}
