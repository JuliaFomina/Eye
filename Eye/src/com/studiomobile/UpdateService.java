package com.studiomobile;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Date;

public class UpdateService extends Service {
    static final String ACTION_UPDATE = "android.appwidget.action.UPDATE";
    static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    static final String ACTION_ON_CLICK = "android.EyeWidget.ACTION_ON_CLICK";
    static final String ACTION_START_ACTIVITY = "android.EyeWidget.ACTION_START_ACTIVITY";
    static final String ACTION_CHANGE_DAY_NIGHT = "android.EyeWidget.ACTION_CHANGE_DAY_NIGHT";

    static final String TOAST_TEXT = "I like when you click on me";

    static final int DAY_HOUR = 9;
    static final int NIGHT_HOUR = 22;

    private final static IntentFilter intentFilter;

    static {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SMS_RECEIVED);
        intentFilter.addAction(ACTION_ON_CLICK);
        intentFilter.addAction(ACTION_START_ACTIVITY);
        intentFilter.addAction(ACTION_CHANGE_DAY_NIGHT);
    }


    public final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(ACTION_SMS_RECEIVED)) {
                reactOnSms(context);
            } else if (action.equals(ACTION_ON_CLICK)) {
                onCLick(context);
            } else if (action.equals(ACTION_START_ACTIVITY)) {
                reactOnStartActivity();
            } else if (action.equals(ACTION_CHANGE_DAY_NIGHT)) {
                reactOnChangeDayNight(context);
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
    }

    private void reactOnSms(Context context) {
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.main);
        views.setImageViewResource(R.id.widget_imageview, R.drawable.fluffy_with_message);
        ComponentName widget = new ComponentName(this, EyeWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(widget, views);
    }

    private void onCLick(Context context) {
        Toast.makeText(context, TOAST_TEXT, Toast.LENGTH_SHORT).show();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
        views.setImageViewResource(R.id.widget_imageview, R.drawable.fluffy_pressed);
        ComponentName widget = new ComponentName(context, EyeWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(widget, views);

        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.click);
        mediaPlayer.start();
        MediaPlayerListener playerCompletionListener = new MediaPlayerListener(context, mediaPlayer);
        mediaPlayer.setOnCompletionListener(playerCompletionListener);
    }

    private void reactOnStartActivity() {
        /* start main activity */
        Intent startActivityIntent = new Intent(getBaseContext(), EyeActivity.class);
        startActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(startActivityIntent);
    }

    private void reactOnChangeDayNight(Context context) {
        Date date = new Date();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
        /* for test */
        if (date.getSeconds() >= 30 && date.getHours() < 60) {
            views.setViewVisibility(R.id.widget_textview, View.INVISIBLE);
        } else {
            views.setViewVisibility(R.id.widget_textview, View.VISIBLE);
        }


//        if(date.getHours()>= DAY_HOUR && date.getHours() < NIGHT_HOUR){
//            views.setImageViewResource(R.id.widget_imageview, R.drawable.fluffy_with_message);
//        }else{
//            views.setImageViewResource(R.id.widget_imageview, R.drawable.fluffy_widget);
//        }

        ComponentName widget = new ComponentName(context, EyeWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(widget, views);
    }
}

