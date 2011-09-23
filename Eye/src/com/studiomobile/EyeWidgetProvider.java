package com.studiomobile;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.*;
import android.os.IBinder;
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
        context.startService(new Intent(UpdateService.ACTION_UPDATE));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
        Intent widgetClick = new Intent(context, EyeWidgetProvider.class);
        widgetClick.setAction("android.EyeWidget.ACTION_ON_CLICK");
        PendingIntent pendingIntentViewClick = PendingIntent.getBroadcast(context, 0, widgetClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_imageview, pendingIntentViewClick);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

        context.startService(new Intent(UpdateService.ACTION_UPDATE));
    }

    /**
     * Setups a BroadcastReceiver and listens for the events.
     */
    public static class UpdateService extends Service {

        static final String ACTION_UPDATE = "android.appwidget.action.UPDATE";
        static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
        static final  String ACTION_ON_CLICK = "android.EyeWidget.ACTION_ON_CLICK";

        private final static IntentFilter intentFilter;

        static {
            intentFilter = new IntentFilter();
//            intentFilter.addAction(Intent.ACTION_TIME_TICK);
            intentFilter.addAction(ACTION_SMS_RECEIVED);
            intentFilter.addAction(Intent.ACTION_PICK);
        }


        public final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
//                if (action.equals(Intent.ACTION_TIME_TICK)) {
//                    update();
//                }
                if (action.equals(ACTION_SMS_RECEIVED)) {
                    reactOnSms(context);
                }
                if (action.equals(ACTION_ON_CLICK)) {
                    onCLick(context);
                }
            }
        };

        @Override
        public void onCreate() {
            super.onCreate();
            registerReceiver(broadcastReceiver, intentFilter);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            unregisterReceiver(broadcastReceiver);
        }

        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);
            if (ACTION_UPDATE.equals(intent.getAction())) {
                update();
            }
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        /**
         * Updates and redraws the Widget.
         */
        private void update() {
//            DateFormat format = new SimpleDateFormat("hh:mm");
//            RemoteViews views = new RemoteViews(getPackageName(), R.layout.main);
//            views.setTextViewText(R.id.widget_textview, format.format(new Date()));
//
//            ComponentName widget = new ComponentName(this, EyeWidgetProvider.class);
//            AppWidgetManager manager = AppWidgetManager.getInstance(this);
//            manager.updateAppWidget(widget, views);
        }

        private void reactOnSms(Context context) {
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.main);
            views.setImageViewResource(R.id.widget_imageview, R.drawable.fluffy_with_message);
            ComponentName widget = new ComponentName(this, EyeWidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(widget, views);
        }

        public void onCLick(Context context) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
            views.setImageViewResource(R.id.widget_imageview, R.drawable.fluffy_pressed);
            ComponentName widget = new ComponentName(context, EyeWidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(widget, views);
        }
    }

}
