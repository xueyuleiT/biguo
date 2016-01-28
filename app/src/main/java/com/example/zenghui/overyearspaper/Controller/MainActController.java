package com.example.zenghui.overyearspaper.Controller;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.zenghui.overyearspaper.BaseActivity;
import com.example.zenghui.overyearspaper.Fragments.DownAndUploadFragment;
import com.example.zenghui.overyearspaper.Fragments.Fragment_three;
import com.example.zenghui.overyearspapers.R;

/**
 * Created by zenghui on 16/1/24.
 */
public class MainActController implements View.OnClickListener {
    public TextView title;
    public FragmentManager fragmentManager;
    public Fragment[] mFragments;
    public RadioButton imgOne, imgTwo, imgThree;
    public int showFragment = 0;
    public NavigationView mNavigationView;
    public DrawerLayout mDrawerLayout;
    public Toolbar mToolbar;
    public Menu menuItem;
    public BaseActivity activity;

    public MainActController(BaseActivity activity) {
        this.activity = activity;

    }

    public void initCompoments() {
        //设置ToolBar
        mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        activity.setSupportActionBar(mToolbar);
        title = ((TextView) activity.findViewById(R.id.title));


        //设置抽屉DrawerLayout
        mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mNavigationView = (NavigationView) activity.findViewById(R.id.navigation_view);

        imgTwo = (RadioButton) activity.findViewById(R.id.imgTwo);
        imgOne = (RadioButton) activity.findViewById(R.id.imgOne);
        imgThree = (RadioButton) activity.findViewById(R.id.imgThree);

    }

    public void setFragmentIndicator(int whichIsDefault) {
        mFragments = new Fragment[3];
        fragmentManager = activity.getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(
                R.id.OneFragment);
        mFragments[1] = fragmentManager.findFragmentById(
                R.id.TwoFragment);
        mFragments[2] = fragmentManager.findFragmentById(
                R.id.ThreeFragment);
        fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[2])
                .hide(mFragments[1]).show(mFragments[whichIsDefault]).commit();
        imgOne.setChecked(true);

    }

    public void initLinstener() {
        imgTwo.setOnClickListener(this);
        imgOne.setOnClickListener(this);
        imgThree.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_one:
                        break;
                    case R.id.item_two:
                        break;
                    case R.id.item_three:
                        break;
                }
                menuItem.setChecked(true);//点击了把它设为选中状态
                mDrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgTwo:
                if (showFragment != 1) {
                    menuItem.findItem(R.id.action_search).setVisible(false);
                    activity.getSupportFragmentManager().beginTransaction()
                            .hide(mFragments[0]).hide(mFragments[2]).show(mFragments[1]).commit();
                    showFragment = 1;
                    ((DownAndUploadFragment) mFragments[1]).init();
                    title.setText("imgTwo");
                }
                break;
            case R.id.imgOne:
                if (showFragment != 0) {
                    menuItem.findItem(R.id.action_search).setVisible(true);
                    activity.getSupportFragmentManager().beginTransaction()
                            .hide(mFragments[1]).hide(mFragments[2]).show(mFragments[0]).commit();
                    showFragment = 0;
                    title.setText("imgOne");
                }
                break;
            case R.id.imgThree:
                if (showFragment != 2) {
                    menuItem.findItem(R.id.action_search).setVisible(false);
                    activity.getSupportFragmentManager().beginTransaction()
                            .hide(mFragments[1]).hide(mFragments[0]).show(mFragments[2]).commit();
                    ((Fragment_three) mFragments[2]).init();
                    showFragment = 2;
                    title.setText("imgThree");
                }
                break;

        }
    }
}
