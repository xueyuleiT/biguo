package com.example.zenghui.overyearspaper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zenghui.overyearspaper.Controller.DownAndUploadFragmentController;
import com.example.zenghui.overyearspapers.R;


/**
 * Created by zenghui on 16/1/10.
 */
public class DownAndUploadFragment extends BaseFragment{

    private View rootView;
    DownAndUploadFragmentController downAndUploadFragmentController;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_down_upload, null);
            downAndUploadFragmentController = new DownAndUploadFragmentController(this,rootView);
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

    boolean isInit = false;

    public void init() {
        if (!isInit) {
            downAndUploadFragmentController.initView();
//            load = (LinearLayout) rootView.findViewById(R.id.load);
//            upload = (LinearLayout) rootView.findViewById(R.id.upload);
//            uploadding = (LinearLayout) rootView.findViewById(R.id.uploadding);
//            loadding = (LinearLayout) rootView.findViewById(R.id.loadding);
//
//
//            load.setOnClickListener(this);
//            upload.setOnClickListener(this);
//            uploadding.setOnClickListener(this);
//            loadding.setOnClickListener(this);
//            listView = (MyListView) rootView.findViewById(R.id.listView);
//            top = (RelativeLayout) rootView.findViewById(R.id.top);
////            myScrollerView = (MyScrollerView) rootView;
//            isInit = true;
//            List<DownLoadData> downLoadDataList = new ArrayList<DownLoadData>();
//            for (int i = 0 ; i < 20; i++){
//                DownLoadData downLoadData = new DownLoadData();
//                downLoadData.setPath("dssdfs");
//                downLoadData.setPercent(11);
//                downLoadDataList.add(downLoadData);
//            }
////            myScrollerView.topHeight = top.getHeight();
////            myScrollerView.listView = listView;
//            downLoadAdapter = new DownLoadAdapter(getActivity(),downLoadDataList);
//            ViewGroup.LayoutParams params = listView.getLayoutParams();
//            params.height = Common.screamHeight*2/3;
//            listView.setLayoutParams(params);
//            listView.setAdapter(downLoadAdapter);
//            initSlidingDrawer();
        }
    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(getActivity(), UpAndDownActivity.class);
//        Bundle bundle = new Bundle();
//        switch (v.getId()) {
//            case R.id.load:
//                bundle.putString("type","load");
//                break;
//            case R.id.loadding:
//                bundle.putString("type","loadding");
//                break;
//            case R.id.upload:
//                bundle.putString("type","upload");
//                break;
//            case R.id.uploadding:
//                bundle.putString("type","uploadding");
//                break;
//        }
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }

//    public void setListViewHeightBasedOnChildren(ListView listView) {
//        if (listView == null)
//            return;
//
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = 0;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }


//    private void initSlidingDrawer( ){
//        mDirectionImageView = (ImageView) rootView.findViewById(R.id.directionImage);
//        mHandleTextView = (TextView) rootView.findViewById(R.id.handTextView);
//        mHandleTextView.setText("展开");
//        mSlidingDrawer = (SlidingDrawer) rootView.findViewById(R.id.slidingDrawer);
//        mSlidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() { // 打开抽屉
//            @Override
//            public void onDrawerOpened() {
//                mDirectionImageView.setImageResource(R.drawable.update_bg);
//                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.arrowrote);
//                mDirectionImageView.setAnimation(animation);
//                mHandleTextView.setText("收起");
//            }
//        });
//        mSlidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() { // 关闭抽屉
//            @Override
//            public void onDrawerClosed() {
//                mDirectionImageView.setImageResource(R.drawable.update_bg);
//                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.arrowrote);
//                mDirectionImageView.setAnimation(animation);
//                mHandleTextView.setText("展开");
//            }
//        });
//    }
//
//    private ImageView mDirectionImageView = null;
//    private SlidingDrawer mSlidingDrawer = null;
//    private TextView mHandleTextView = null;

}
