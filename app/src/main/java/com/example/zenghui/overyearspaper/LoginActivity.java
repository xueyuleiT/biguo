package com.example.zenghui.overyearspaper;

import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zenghui.overyearspaper.Controller.LoginActController;
import com.example.zenghui.overyearspaper.Utils.Common;
import com.example.zenghui.overyearspapers.R;

/**
 * A login screen that offers login via phone number/password.
 */
public class LoginActivity extends BaseActivity {

    // UI references.
    private Toolbar mToolbar;
    private TextView mTitle;
    LoginActController loginActController;

    @Override
    void initViews() {
        setContentView(R.layout.activity_login);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.title);
        mToolbar.setTitle("");
        mTitle.setText(getString(R.string.login));
        setSupportActionBar(mToolbar);

        if (Common.screamWidth == 0) {
            Display display = getWindowManager().getDefaultDisplay();
            Common.screamWidth = display.getWidth();
            Common.screamHeight = display.getHeight();

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        loginActController = new LoginActController(this);
        loginActController.initView();
    }

    @Override
    void initDatas() {

    }

    @Override
    void doOther() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.register_button:
                loginActController.startIntent(RegisterActivity.class, null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

