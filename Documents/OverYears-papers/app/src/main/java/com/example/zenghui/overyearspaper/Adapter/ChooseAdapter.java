package com.example.zenghui.overyearspaper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zenghui.overyearspaper.Model.ChooseInfo;
import com.example.zenghui.overyearspapers.R;

import java.util.ArrayList;

/**
 * Created by zenghui on 16/1/12.
 */
public class ChooseAdapter extends BaseAdapter {

    Context context;
    int SelectPosition;
    ArrayList<ChooseInfo> dataList;

    public ChooseAdapter(Context context, ArrayList<ChooseInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.data_item, null);
            viewHolder.tv = ((TextView) convertView.findViewById(R.id.tv));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(dataList.get(position).getName());

        if (dataList.get(position).isSelect()) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.gray_deep));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.ttf_title_bg));
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }
}
