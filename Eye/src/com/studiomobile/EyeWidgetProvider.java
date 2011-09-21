package com.studiomobile;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;


import java.text.SimpleDateFormat;
import java.util.*;

public class EyeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 1000);
    }

    private class MyTime extends TimerTask {
        RemoteViews remoteViews;
        AppWidgetManager appWidgetManager;
        ComponentName thisWidget;
        java.text.DateFormat format = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM,
                Locale.getDefault());

        public MyTime(Context context, AppWidgetManager appWidgetManager) {
            this.appWidgetManager = appWidgetManager;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            thisWidget = new ComponentName(context, EyeWidgetProvider.class);
        }

        @Override
        public void run() {
            remoteViews.setTextViewText(R.id.widget_textview, format.format(new Date()));
            appWidgetManager.updateAppWidget(thisWidget, remoteViews);
        }

    }


}
