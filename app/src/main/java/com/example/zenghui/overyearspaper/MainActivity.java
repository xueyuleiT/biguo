package com.example.zenghui.overyearspaper;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.zenghui.overyearspaper.Controller.MainActController;
import com.example.zenghui.overyearspaper.Fragments.SearchFragment;
import com.example.zenghui.overyearspaper.Utils.Common;
import com.example.zenghui.overyearspapers.R;

public class MainActivity extends BaseActivity {
    MainActController mainActController;

    @Override
    void initViews() {
        setContentView(R.layout.activity_main);
        mainActController = new MainActController(this);
        mainActController.initCompoments();
        mainActController.initLinstener();
        mainActController.setFragmentIndicator(0);
        WindowManager wm = this.getWindowManager();
        Common.screamHeight = wm.getDefaultDisplay().getHeight();
        Common.screamWidth = wm.getDefaultDisplay().getWidth();
    }

    @Override
    void initDatas() {

    }

    @Override
    void doOther() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mainActController.menuItem = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ((SearchFragment) mainActController.mFragments[0]).showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        if (!((SearchFragment) mainActController.mFragments[0]).goBack()) {
            super.onBackPressed();
        }
    }
}
