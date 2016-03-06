package com.example.zenghui.overyearspaper.Fragments;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zenghui.overyearspaper.Adapter.ChooseAdapter;
import com.example.zenghui.overyearspaper.Adapter.DownLoadAdapter;
import com.example.zenghui.overyearspaper.Model.ChooseInfo;
import com.example.zenghui.overyearspaper.Model.DownLoadData;
import com.example.zenghui.overyearspapers.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zenghui on 16/1/10.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    ListView adacemy, perfession, calss;
    ListView searchList;
    ChooseAdapter adacemyAdapter, perfessionAdapter, calssAdapter;
    TextView textView;
    Button rightBtn;
    LinearLayout chooseView;
    private ImageView more;
    ArrayList<ChooseInfo> perfessionList = new ArrayList<ChooseInfo>();
    ArrayList<ChooseInfo> calssList = new ArrayList<ChooseInfo>();
    int[] location;
    DownLoadAdapter downLoadAdapter;

    Button cancel,ok;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_search, null);
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
        init();
        initLinstener();
    }


    void init() {
        adacemy = (ListView) rootView.findViewById(R.id.adacemy);
        perfession = (ListView) rootView.findViewById(R.id.perfession);
        calss = (ListView) rootView.findViewById(R.id.calss);
        chooseView = (LinearLayout) rootView.findViewById(R.id.chooseView);
        searchList = (ListView) rootView.findViewById(R.id.searchList);

        cancel = (Button) rootView.findViewById(R.id.cancel);
        ok = (Button) rootView.findViewById(R.id.ok);

        List<DownLoadData> downLoadDataList = new ArrayList<DownLoadData>();
        for (int i = 0; i < 10; i++) {
            DownLoadData downLoadData = new DownLoadData();
            downLoadDataList.add(downLoadData);
        }

        downLoadAdapter = new DownLoadAdapter(getActivity(), downLoadDataList);
        searchList.setAdapter(downLoadAdapter);
    }


    void initLinstener() {

        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        adacemy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                int length = dataList.size();
                for (int i = 0; i < length; i++) {
                    if (dataList.get(i).isSelect()) {
                        if (i == position) {
                            return;
                        }
                        dataList.get(i).setSelect(false);

                        break;
                    }
                }

                dataList.get(position).setSelect(true);
                adacemyAdapter.notifyDataSetChanged();

                if (perfession.getVisibility() == View.INVISIBLE) {
                    perfession.setVisibility(View.VISIBLE);
                }

                perfessionList.clear();
                for (int i = 0; i < 10; i++) {
                    ChooseInfo info = new ChooseInfo();
                    info.setName("信息与科学");
                    info.setSelect(false);
                    info.setType(1);
                    perfessionList.add(info);
                }

                perfessionAdapter = new ChooseAdapter(getActivity(), perfessionList);

                perfession.setAdapter(perfessionAdapter);
                calss.setVisibility(View.INVISIBLE);


            }
        });

        perfession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                int length = perfessionList.size();
                for (int i = 0; i < length; i++) {
                    if (perfessionList.get(i).isSelect()) {
                        if (i == position) {
                            return;
                        }
                        perfessionList.get(i).setSelect(false);

                        break;
                    }
                }

                perfessionList.get(position).setSelect(true);
                perfessionAdapter.notifyDataSetChanged();

                if (calss.getVisibility() == View.INVISIBLE) {
                    calss.setVisibility(View.VISIBLE);
                }

                calssList.clear();
                for (int i = 0; i < 20; i++) {
                    ChooseInfo info = new ChooseInfo();
                    info.setName("信息与科学");
                    info.setSelect(false);
                    info.setType(2);
                    calssList.add(info);
                }

                calssAdapter = new ChooseAdapter(getActivity(), calssList);
                calss.setAdapter(calssAdapter);

            }
        });
        calss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int length = calssList.size();
                for (int i = 0; i < length; i++) {
                    if (calssList.get(i).isSelect()) {
                        calssList.get(i).setSelect(false);
                        break;
                    }
                }
                calssList.get(position).setSelect(true);
                calssAdapter.notifyDataSetChanged();
            }
        });
    }

    ArrayList<ChooseInfo> dataList;
    Animation startAnimator, endAnimator;
    boolean isShow = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more:
                showDialog();
                break;

            case R.id.ok:
                showDialog();
                break;

            case R.id.cancel:
                showDialog();
                break;

        }

    }


    /**
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }


    public void showDialog() {
        if (isShow) {
            isShow = false;
            chooseView.setVisibility(View.GONE);
            if (endAnimator == null) {
                endAnimator = AnimationUtils.loadAnimation(getActivity(), R.anim.up_out);
                endAnimator.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
            }
            if (startAnimator != null && startAnimator.hasStarted() && !startAnimator.hasEnded()) {
                startAnimator.cancel();
            }
            chooseView.startAnimation(endAnimator);
        } else {
            isShow = true;
            chooseView.setVisibility(View.VISIBLE);
            if (adacemyAdapter == null) {
                dataList = new ArrayList<ChooseInfo>();
                for (int i = 0; i < 15; i++) {
                    ChooseInfo info = new ChooseInfo();
                    info.setName("信息与科学");
                    info.setSelect(false);
                    info.setType(0);
                    dataList.add(info);
                }
                adacemyAdapter = new ChooseAdapter(getActivity(), dataList);
                adacemy.setAdapter(adacemyAdapter);
                adacemy.setVisibility(View.VISIBLE);
            }
            if (startAnimator == null) {
                startAnimator = AnimationUtils.loadAnimation(getActivity(), R.anim.up_in);
                startAnimator.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
            }

            if (endAnimator != null && endAnimator.hasStarted() && !endAnimator.hasEnded()) {
                endAnimator.cancel();
            }

            chooseView.startAnimation(startAnimator);
        }
    }

    public boolean goBack() {
        if (canBack()) {
            showDialog();
            return true;
        }
        return false;
    }

    public boolean canBack() {
        if (chooseView.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

}
