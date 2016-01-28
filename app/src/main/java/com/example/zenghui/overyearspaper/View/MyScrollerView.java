package com.example.zenghui.overyearspaper.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by zenghui on 16/1/22.
 */
public class MyScrollerView extends ScrollView {
    public MyListView listView;
    public int topHeight;

    public MyScrollerView(Context context) {
        super(context);
    }

    public MyScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (listView != null && listView.getChildCount() > 0 && listView.getVisibility() == View.VISIBLE) {
            int[] location = new int[2];
            listView.getLocationOnScreen(location);
            if (ev.getX() > location[0] & ev.getX() < listView.getWidth()) {
                if (ev.getY() > location[1] & ev.getY() < location[1] + listView.getHeight()) {
                    Log.d("","listView.status ======>"+listView.status +"   listView.isUp =====>"+listView.isUp);
                    if(listView.status == 2 & !listView.isUp){
                        return super.onInterceptTouchEvent(ev);
                    }else if(listView.status == 1 & listView.isUp){
                        return super.onInterceptTouchEvent(ev);
                    }else{
                        return false;
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
