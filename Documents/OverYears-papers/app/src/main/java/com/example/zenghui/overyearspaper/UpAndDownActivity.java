package com.example.zenghui.overyearspaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zenghui.overyearspaper.Adapter.DownLoadAdapter;
import com.example.zenghui.overyearspaper.Model.DownLoadData;
import com.example.zenghui.overyearspapers.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zenghui on 16/1/23.
 */
public class UpAndDownActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ListView listView;
    DownLoadAdapter downLoadAdapter;
    List<DownLoadData> downLoadDataList = new ArrayList<DownLoadData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upandload_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        String title = getIntent().getStringExtra("type");
        if (title.equals("load")) {
            title = "下载";
        } else if (title.equals("loadding")) {
            title = "下载中";
        } else if (title.equals("upload")) {
            title = "上传";
        } else if (title.equals("uploadding")) {
            title = "上传中";
        }
        ((TextView) findViewById(R.id.title)).setText(title);

        for (int i = 0; i < 20; i++) {
            DownLoadData downLoadData = new DownLoadData();
            downLoadData.setPath("dssdfs");
            downLoadData.setPercent(11);
            downLoadDataList.add(downLoadData);
        }
        downLoadAdapter = new DownLoadAdapter(this, downLoadDataList);
        listView.setAdapter(downLoadAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
