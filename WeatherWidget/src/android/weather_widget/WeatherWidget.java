package android.weather_widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import com.example.R;

/**
 * Created by IntelliJ IDEA.
 * User: julia
 * Date: 9/20/11
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class WeatherWidget extends AppWidgetProvider{


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
        int[] appWidgetIds){
    }

//    public void onClick(View view){
//        ImageView imageView = (ImageView) view.findViewById(R.id.widget_imageview);
//        imageView.setImageResource(R.drawable.fluffy_pressed);
//    }

}
