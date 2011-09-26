package com.studiomobile;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.RemoteViews;

public class MediaPlayerListener implements MediaPlayer.OnCompletionListener{
    Context context;
    MediaPlayer mediaPlayer;

    MediaPlayerListener(Context context, MediaPlayer mediaPlayer){
        this.context = context;
        this.mediaPlayer = mediaPlayer;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
        views.setImageViewResource(R.id.widget_imageview, R.drawable.fluffy_widget);
        ComponentName widget = new ComponentName(context, EyeWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(widget, views);
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
