package com.example.philipcanniff.p_canniff_recipewidgets.Widget_One;

import android.content.Intent;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by philipcanniff on 10/19/15.
 */
public class WidgetOneService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetOneFactory(getApplicationContext());
    }

}
