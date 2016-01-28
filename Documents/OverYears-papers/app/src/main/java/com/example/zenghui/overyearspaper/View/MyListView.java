package com.example.zenghui.overyearspaper.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zenghui on 16/1/23.
 */
public class MyListView extends ListView{

    public int status = 0;
    public boolean isUp;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public int lastVisibleItemPosition = 0;
}
