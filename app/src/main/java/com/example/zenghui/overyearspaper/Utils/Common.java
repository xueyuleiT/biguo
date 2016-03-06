package com.example.zenghui.overyearspaper.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.RestAdapter;

/**
 * Created by zenghui on 16/1/13.
 */
public class Common {
    public static String PHONE_PATTERN ="^[1][3,4,7,5,8][0-9]{9}$";
    public static int screamHeight = 0, screamWidth = 0;
    public static String DOMAIN = "";
    public static long downTime = 0;//退出点击的时间

    public static ITask getItask(String apiUrl) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(apiUrl)
                .setClient(OkHttpUtils.getOkHttpClientIntance())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ITask task = restAdapter.create(ITask.class);
        return task;
    }

    /**
     * 校验电话号码
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneValid(String phone) {
        Pattern pattern =Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public  static void showSoftIput(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public  static void hideSoftIput(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 校验短信验证码
     *
     * @param message
     * @return
     */
    public static boolean isMessageValid(String message) {
        return message.length() >= 4;
    }
    /**
     * 校验密码
     *
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {
        return !TextUtils.isEmpty(password);
    }

}
