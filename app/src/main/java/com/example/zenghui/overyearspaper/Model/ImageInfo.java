package com.example.zenghui.overyearspaper.Model;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

import com.example.zenghui.overyearspapers.R;


/**
 * Created by zenghui on 15/12/23.
 */
public class ImageInfo {

    int x, y, showX, width, mAlpha, padding = 0;
    int authImg;
    int status = 0;//0 已认证 1 未认证 2 已过期
    int textColor;
    String text;
    String describe;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    BitmapDrawable bmp;
    boolean isPressed = false;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public int getShowX() {
        return showX;
    }

    public void setShowX(int showX) {
        this.showX = showX;
    }

    public int getPadding() {
        return padding;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public BitmapDrawable getBmp() {
        return bmp;
    }

    public void setBmp(BitmapDrawable bmp) {
        this.bmp = bmp;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getResource() {
        return authImg;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setResource(int authImg) {
        this.authImg = authImg;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    int textSize = 0;

    public int getTextSize(Context context, float pecent) {
        if (textSize == 0) {
            textSize = context.getResources().getDimensionPixelSize(R.dimen.public_space_value_14);
        }
        return (int) (pecent * textSize);
    }

    public int getTextHeight(int mWidth, float width) {
        Paint pFont = new Paint();
        Rect rect = new Rect();
        pFont.getTextBounds(getText(), 0, 1, rect);
        return (int) ((mWidth / (width)) * rect.height());
    }

    public int getWidth(int expandX, int width) {
        return (int) (width - Math.abs(0.3 * Math.abs(getX() - (width * 2 - expandX))));
    }

    public boolean isOnCircle(int x, int y, int mWidth, int width, int top) {
        int cx = getShowX() + (mWidth) / 2;
        int cy = top + mWidth / 2;
        if (Math.pow((x - cx), 2) + Math.pow((y - cy), 2) <= Math.pow(mWidth / 2, 2)) {
            return true;
        }

        return false;
    }

}
