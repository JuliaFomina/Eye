package com.studiomobile;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.*;
import android.widget.RemoteViews;


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

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            Intent widgetIntent = new Intent(UpdateService.ACTION_ON_CLICK);
            PendingIntent pendingIntentViewClick = PendingIntent.getBroadcast(context, 0, widgetIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_imageview, pendingIntentViewClick);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }


}
