package com.example.zenghui.overyearspaper.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zenghui.overyearspapers.R;


/**
 * Created by zenghui on 16/1/10.
 */
public class Fragment_three extends BaseFragment {
    private View rootView;

    private boolean isInit = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_three, null);
        }
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void init() {

        if (!isInit) {
            isInit = true;
            Log.d("", "init ===========>");
        }

    }

}
