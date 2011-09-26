package com.studiomobile;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

public class UpdateService extends Service {
    static final String ACTION_UPDATE = "android.appwidget.action.UPDATE";
        static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
        static final String ACTION_ON_CLICK = "android.EyeWidget.ACTION_ON_CLICK";

        private final static IntentFilter intentFilter;

        static {
            intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_SMS_RECEIVED);
            intentFilter.addAction(ACTION_ON_CLICK);
        }


        public final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();

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
         * Now this method isn't used but it will helpful in counting new incoming message
         * (to know when there is no any new message)
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
            Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
            views.setImageViewResource(R.id.widget_imageview, R.drawable.fluffy_pressed);
            ComponentName widget = new ComponentName(context, EyeWidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(widget, views);


            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.click);
            mediaPlayer.start();
        }
}

