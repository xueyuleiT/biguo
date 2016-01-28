package com.example.zenghui.overyearspaper.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.zenghui.overyearspaper.Model.DownLoadData;
import com.example.zenghui.overyearspapers.R;

import java.util.List;

/**
 * Created by zenghui on 16/1/22.
 */
public class DownLoadAdapter extends BaseAdapter {
    Context context;
    List<DownLoadData> list;

    public DownLoadAdapter(Context context, List<DownLoadData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            Log.d("","111111");
            convertView = LayoutInflater.from(context).inflate(R.layout.download_item,null);
        }

        return convertView;
    }
}
