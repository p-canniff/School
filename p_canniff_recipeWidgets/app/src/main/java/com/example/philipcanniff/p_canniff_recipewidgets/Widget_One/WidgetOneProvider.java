package com.example.philipcanniff.p_canniff_recipewidgets.Widget_One;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.philipcanniff.p_canniff_recipewidgets.Activites.DetailActivity;
import com.example.philipcanniff.p_canniff_recipewidgets.R;

/**
 * Created by philipcanniff on 10/19/15.
 */
public class WidgetOneProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        for(int i = 0; i < appWidgetIds.length; i++) {

            int widgetId = appWidgetIds[i];

            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_one_layout);

            Intent intent = new Intent(context, WidgetOneService.class);
            widget.setRemoteAdapter(R.id.widgetViewFlipper, intent);
            //widget.setEmptyView(R.id.widgetViewFlipper, R.id.empty);

            Intent detailIntent = new Intent(context, DetailActivity.class);
            PendingIntent detailPIntent = PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.widgetViewFlipper, detailPIntent);

            appWidgetManager.updateAppWidget(widgetId, widget);
        }

    }

    public static void updateWidget(Context c){

        ComponentName name = new ComponentName(c.getPackageName(), WidgetOneProvider.class.getName());
        int[] widgetIds = AppWidgetManager.getInstance(c).getAppWidgetIds(name);
        AppWidgetManager.getInstance(c).notifyAppWidgetViewDataChanged(widgetIds, R.id.widgetViewFlipper);

        Log.i("a", "a: .... ");

    }

}
