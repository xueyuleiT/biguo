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
        if (mainActController.showFragment == 0) {
            if (!((SearchFragment) mainActController.mFragments[0]).goBack()) {
                doubleClickExitApp();
            }
        } else {
            doubleClickExitApp();
        }

    }

    private void doubleClickExitApp() {
        if (Common.downTime == 0) {
            Common.downTime = System.currentTimeMillis();
            showToast(R.string.double_click_exit_app);
            return;
        }
        long lastDownTime = System.currentTimeMillis();
        if ((lastDownTime - Common.downTime) > 1000) {
            Common.downTime = lastDownTime;
            showToast(R.string.double_click_exit_app);
        } else {
            System.exit(0);
        }
    }
}
