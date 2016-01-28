package com.example.zenghui.overyearspaper.Controller;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zenghui.overyearspaper.Adapter.DownLoadAdapter;
import com.example.zenghui.overyearspaper.Fragments.BaseFragment;
import com.example.zenghui.overyearspaper.Listener.CircleClick;
import com.example.zenghui.overyearspaper.Model.DownLoadData;
import com.example.zenghui.overyearspaper.View.LoopView;
import com.example.zenghui.overyearspapers.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zenghui on 16/1/24.
 */
public class DownAndUploadFragmentController {
    BaseFragment baseFragment;
    public View rootView;
    public ListView listView;
    public LoopView loopView;
    public DownLoadAdapter downLoadAdapter;
    List<DownLoadData> downLoadDataList;

    public DownAndUploadFragmentController(BaseFragment baseFragment, View rootView) {
        this.baseFragment = baseFragment;
        this.rootView = rootView;
    }

    public void initView() {
        loopView = (LoopView) rootView.findViewById(R.id.loopView);
        listView = (ListView) rootView.findViewById(R.id.listView);
        loopView.textView = (TextView) rootView.findViewById(R.id.describe);

        downLoadDataList = new ArrayList<DownLoadData>();

        for (int i = 0; i < 10; i++) {
            DownLoadData downLoadData = new DownLoadData();
            downLoadData.setPath("ddfsdsds");
            downLoadDataList.add(downLoadData);
        }

        initListView(downLoadDataList);

        int[] status = new int[]{1, 1, 1, 1, 1, 1};
        loopView.setImageInfoList(status);
        initListener();
    }

    public void initListView(List<DownLoadData> list) {
        if (downLoadAdapter == null) {
            downLoadAdapter = new DownLoadAdapter(baseFragment.getActivity(), list);
        }
        listView.setAdapter(downLoadAdapter);
    }

    void initListener() {
        loopView.onClick(new CircleClick() {
            @Override
            public void click(int index) {
                DownLoadData downLoadData = new DownLoadData();
                downLoadData.setPath("11111");
                downLoadDataList.add(0, downLoadData);
                downLoadAdapter.notifyDataSetChanged();
            }
        });
    }
}
