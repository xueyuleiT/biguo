package com.example.zenghui.overyearspaper.View;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.zenghui.overyearspaper.Listener.CircleClick;
import com.example.zenghui.overyearspaper.Listener.OnCircleClick;
import com.example.zenghui.overyearspaper.Model.DeceIntEvaluator;
import com.example.zenghui.overyearspaper.Model.ImageInfo;
import com.example.zenghui.overyearspaper.Utils.Common;
import com.example.zenghui.overyearspapers.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zenghui on 15/12/24.
 */
public class LoopView extends View implements OnCircleClick {
    private VelocityTracker vTracker = null;
    List<ImageInfo> imageInfoList = new ArrayList<ImageInfo>();
    int size = 6;
    int visiCount = 5;
    int textSize;
    int times, height;
    int currentIndex = -1;
    int decream = Common.screamHeight / 8;
    int shakeX = Common.screamWidth / 100;
    ValueAnimator valueAnimator;
    Paint paint;
    public TextView textView;

    public List<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public LoopView(Context context) {
        super(context);
        init();
    }

    public LoopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        paint = new Paint();
        textSize = getResources().getDimensionPixelSize(R.dimen.public_space_value_14);
        times = Common.screamWidth / 240;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (imageInfoList.size() == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            ImageInfo imageInfo = imageInfoList.get(i);
            if (imageInfo.getX() >= Common.screamWidth || imageInfo.getX() + width <= 0) {
                continue;
            }
            paint.reset();
            int mWidth = imageInfo.getWidth(expandX, width);
            float base = mWidth / (width * 1.00f);
            float pecent = base * base * 0.9f;
            int textH = imageInfo.getTextHeight(mWidth, width);
            int y = (height - mWidth - textH * times) / 2;
            int x;
            int dw = width - mWidth;
            if (imageInfo.getX() < width * 2 - expandX) {
                int margin = width - expandX - imageInfo.getX();
                if (margin < 0) {
                    x = (int) (imageInfo.getX() + dw * 0.5 - decream * (1 - base));
                } else {
                    x = (int) (imageInfo.getX() + dw * 0.5 - decream * (1 - base) + margin * base);
                }
            } else {
                int margin = imageInfo.getX() - width * 3 + expandX;
                if (margin < 0) {
                    x = (int) (imageInfo.getX() + dw * 0.5 + decream * (1 - base));
                } else {
                    x = (int) (imageInfo.getX() + dw * 0.5 + decream * (1 - base) - margin * base);
                }
            }

            imageInfo.setShowX(x);
            String text = imageInfo.getText();

            paint.setAntiAlias(true);
            paint.setColor(imageInfo.getTextColor());
            paint.setTextSize(imageInfo.getTextSize(getContext(), base));

            if (imageInfo.isPressed()) {
                paint.setAlpha(130);
            } else {
                paint.setAlpha((int) (pecent * 255));
            }

            canvas.drawBitmap(zoomDrawable(imageInfo.getBmp(), mWidth, mWidth), x, y, paint);
            canvas.drawText(text, x + (mWidth - paint.measureText(text)) * 0.5f, y + mWidth + textH * times, paint);
        }

    }

    int width = 0;
    int downX, downY, beforeX;
    long downTime;
    boolean isMove = false, needSet = false, startMove = false, isUp = false;
    boolean isPressed = false, ontouchArea = true, isStop = false;
    int index = -1, startDx = 0;
    Thread thread;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = 0;
                isPressed = false;
                isStop = false;
                index = -1;
                isMove = false;
                startMove = false;
                needSet = false;
                isUp = false;
                if (valueAnimator != null) {
                    if (valueAnimator.isRunning()) {
                        needSet = true;
                    }
                    stopAnimation();
                }

                if (thread != null && thread.isAlive()) {
                    thread.interrupt();
                }

                downX = (int) event.getX();
                downY = (int) event.getY();
                beforeX = downX;

                if (vTracker == null) {
                    vTracker = VelocityTracker.obtain();
                } else {
                    vTracker.clear();
                }
                vTracker.addMovement(event);
                for (int i = 0; i < size; i++) {
                    final ImageInfo imageInfo = imageInfoList.get(i);

                    if (imageInfo.getShowX() >= Common.screamWidth || imageInfo.getShowX() + width <= 0) {
                        continue;
                    }
                    int mWidth = imageInfo.getWidth(expandX, width);
                    int textH = imageInfo.getTextHeight(mWidth, width);
                    int top = (height - mWidth - textH) / 2;

                    if (imageInfo.isOnCircle((int) event.getX(), (int) event.getY(), mWidth, width, top)) {
                        if (Math.abs(imageInfo.getX() - (width * 2 - expandX)) < startDx) {
                            final int mi = i;
                            ontouchArea = true;
                            downTime = System.currentTimeMillis();
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (downTime != 0 & !isStop) {
                                        try {
                                            Thread.sleep(10);
                                            if ((System.currentTimeMillis() - downTime >= 200) & ontouchArea) {
                                                isPressed = true;
                                                imageInfo.setIsPressed(isPressed);
                                                index = mi;
                                                handler.sendEmptyMessage(1);
                                                break;
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                            thread.start();

                        }
                        break;
                    }
                }

                if (downTime == 0) {
                    downTime = System.currentTimeMillis();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                int tempX = (int) (event.getX() - beforeX);
                if (startMove) {
                    if (!isLoop) {
                        if ((Math.abs(imageInfoList.get(0).getX() - width * 2 + expandX) < shakeX)) {
                            if (tempX < 0) {
                                setImageInfoList(tempX);
                                vTracker.addMovement(event);
                            } else {
                                setImageInfoList(0);
                            }
                        } else if ((Math.abs(imageInfoList.get(size - 1).getX() - width * 2 + expandX) < shakeX)) {
                            if (tempX > 0) {
                                setImageInfoList(tempX);
                                vTracker.addMovement(event);
                            } else {
                                setImageInfoList(0);
                            }
                        } else {
                            setImageInfoList(tempX);
                            vTracker.addMovement(event);
                        }

                    } else {
                        setImageInfoList(tempX);
                        vTracker.addMovement(event);
                    }
                } else {
                    vTracker.addMovement(event);
                }
                beforeX = (int) event.getX();

                if (isPressed & (Math.abs(event.getX() - downX) < startDx)) {
                    ontouchArea = true;
                    imageInfoList.get(index).setIsPressed(isPressed);
                } else if ((Math.abs(event.getX() - downX) >= startDx)) {
                    if (!startMove) {
                        startMove = true;
                    }
                    isPressed = false;
                    ontouchArea = false;
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isUp = true;
                beforeValue = 0;
                isStop = true;
                long upTime = System.currentTimeMillis();
                if (upTime - downTime < 200 && Math.abs(event.getX() - downX) < startDx) {
                    int i = 0;
                    for (; i < size; i++) {
                        final ImageInfo imageInfo = imageInfoList.get(i);

                        if (imageInfo.getShowX() >= Common.screamWidth || imageInfo.getShowX() + width <= 0) {
                            continue;
                        }
                        int mWidth = imageInfo.getWidth(expandX, width);
                        int textH = imageInfo.getTextHeight(mWidth, width);
                        int top = (height - mWidth - textH) / 2;

                        if (imageInfo.isOnCircle((int) event.getX(), (int) event.getY(), mWidth, width, top)) {
                            if (Math.abs(imageInfo.getX() - width * 2 + expandX) < startDx) {
                                if (circleClick != null) {
                                    circleClick.click(i);
                                }
                                imageInfo.setIsPressed(true);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        imageInfo.setIsPressed(false);
                                        handler.sendEmptyMessage(2);

                                    }
                                }).start();
                                handler.sendEmptyMessage(1);
                            }
                            currentIndex = i;
                            if (textView != null) {
                                textView.setText(imageInfo.getDescribe());
                            }
                            gotoMiddle((width * 2 - expandX) - imageInfo.getX());
                            return true;
                        }
                    }

                    if (i != size) {
                        break;
                    }
                } else {
                    if (thread == null || !thread.isAlive()) {
                        setImageInfoList(0);
                    }
                }
                if (startMove) {
                    vTracker.computeCurrentVelocity(1000);
                    float xVelocity = vTracker.getXVelocity();
                    if (!isLoop) {
                        if ((Math.abs(imageInfoList.get(0).getX() - width * 2 + expandX) < shakeX)
                                || (Math.abs(imageInfoList.get(size - 1).getX() - width * 2 + expandX) < shakeX)) {
                            setMiddle();
                            break;
                        }
                        xVelocity = xVelocity / 2;
                    }
                    xVelocity = xVelocity * 0.5f;
                    if (((int) xVelocity) == 0) {
                        setMiddle();
                        break;
                    } else if (xVelocity > Common.screamWidth * 20) {
                        xVelocity = Common.screamWidth * 20;
                    } else if (xVelocity < -Common.screamWidth * 20) {
                        xVelocity = -Common.screamWidth * 20;
                    }

                    if (index != -1) {
                        imageInfoList.get(index).setIsPressed(false);
                    }

                    int mDx = -getMiddleDx();
                    int mTimes = (((int) xVelocity) / width);
                    if (xVelocity > 0) {
                        if (mTimes != 0) {
                            xVelocity = mTimes * width - mDx;
                        } else {
                            if (mDx < 0) {
                                if (-mDx >= xVelocity) {
                                    xVelocity = -mDx;
                                } else if (-mDx < xVelocity) {
                                    if ((xVelocity + mDx) * 2 / width != 0) {
                                        xVelocity = width - mDx;
                                    } else {
                                        xVelocity = -mDx;
                                    }
                                }
                            } else {
                                if ((mDx + xVelocity) * 2 / width != 0) {
                                    xVelocity = width - mDx;
                                } else {
                                    xVelocity = -mDx;
                                }
                            }
                        }
                    } else {
                        if (mTimes != 0) {
                            xVelocity = mTimes * width - mDx;
                        } else {
                            if (mDx > 0) {
                                if (mDx >= -xVelocity) {
                                    xVelocity = -mDx;
                                } else if (mDx > 0 & mDx < -xVelocity) {
                                    if ((xVelocity + mDx) * 2 / width != 0) {
                                        xVelocity = -width - mDx;
                                    } else {
                                        xVelocity = -mDx;
                                    }
                                }
                            } else {
                                if ((mDx + xVelocity) * 2 / width != 0) {
                                    xVelocity = -width - mDx;
                                } else
                                    xVelocity = -mDx;
                            }
                        }
                    }

                    final int xVelocityTemp = (int) xVelocity;

                    stopAnimation();


                    valueAnimator = ValueAnimator.ofInt(xVelocityTemp, 0);
                    valueAnimator.setEvaluator(new DeceIntEvaluator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            value = (int) animation.getAnimatedValue();
                            int dx;
                            if (beforeValue == 0 & value != 0) {
                                dx = (xVelocityTemp - value);
                            } else {
                                dx = (beforeValue - value);
                            }
                            setImageInfoList(dx);
                            beforeValue = value;
                            invalidate();
                        }
                    });
                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            beforeValue = 0;
                            setMiddle();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    float time = (xVelocity) / Common.screamWidth;

                    if(isLoop){
                        time = time/2;
                    }

                    valueAnimator.setInterpolator(new DecelerateInterpolator());
                    valueAnimator.setDuration((long) Math.abs(time * 1000));
                    valueAnimator.start();
                }

                if (needSet & !startMove) {
                    setMiddle();
                }
                startMove = false;
                break;
            default:
                setMiddle();
                break;
        }
        invalidate();
        return true;
    }

    int value, beforeValue = 0;
    ImageInfo cacheImage;
    boolean isLoop = true;


    private void stopAnimation() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.removeAllListeners();
            beforeValue = 0;
        }
    }

    private void setImageInfoList(int dx) {

        if (!isLoop) {
            if (imageInfoList.get(0).getX() > width * 2 - expandX & dx > 0) {
                stopAnimation();
                if (textView != null) {
                    textView.setText(imageInfoList.get(0).getDescribe());
                }
                currentIndex = 0;
                gotoMiddle(width * 2 - expandX - imageInfoList.get(0).getX());
//                setMiddle();
                return;
            }

            if (imageInfoList.get(size - 1).getX() < width * 2 - expandX & dx < 0) {
                stopAnimation();
                if (textView != null) {
                    textView.setText(imageInfoList.get(size - 1).getDescribe());
                }
                currentIndex = size - 1;
                gotoMiddle(width * 2 - expandX - imageInfoList.get(size - 1).getX());
//                setMiddle();
                return;
            }

        }

        for (int i = 0; i < size; i++) {
            if (dx != 0) {
                if (dx > width) {
                    if (i == 0) {
                        imageInfoList.get(i).setX(imageInfoList.get(i).getX() + width);
                    } else {
                        imageInfoList.get(i).setX(imageInfoList.get(i - 1).getX() + width);
                    }
                } else if (dx < -width) {
                    if (i == 0) {
                        imageInfoList.get(i).setX(imageInfoList.get(i).getX() - width);
                    } else {
                        imageInfoList.get(i).setX(imageInfoList.get(i - 1).getX() + width);
                    }
                } else {
                    if (i == 0) {
                        imageInfoList.get(i).setX(imageInfoList.get(i).getX() + dx);
                    } else {
                        imageInfoList.get(i).setX(imageInfoList.get(i - 1).getX() + width);
                    }
                }
            }
            imageInfoList.get(i).setIsPressed(false);

        }
        if (isLoop) {
            if (dx < 0) {
                if (imageInfoList.get(size - 1).getX() + width < Common.screamWidth) {
                    cacheImage = imageInfoList.get(0);
                    imageInfoList.remove(0);
                    cacheImage.setX(imageInfoList.get(size - 2).getX() + width);
                    imageInfoList.add(cacheImage);

                }
            } else {
                if (imageInfoList.get(0).getX() > 0) {
                    cacheImage = imageInfoList.get(size - 1);
                    imageInfoList.remove(size - 1);
                    cacheImage.setX(imageInfoList.get(0).getX() - width);
                    imageInfoList.add(0, cacheImage);
                }
            }
        }
    }


    private int getMiddleDx() {
        int moveX = 0;
        int max = 0;
        int i = 0;
        for (; i < size; i++) {
            ImageInfo imageInfo = imageInfoList.get(i);
            if (imageInfo.getShowX() >= Common.screamWidth || imageInfo.getShowX() + width <= 0) {
                continue;
            }
            int mw = imageInfo.getWidth(expandX, width);
            if (mw <= 0) {
                continue;
            }
            if (mw > max) {
                max = mw;
            } else {
                imageInfo = imageInfoList.get(i - 1);
                if (imageInfo.getX() > (width * 2 - expandX)) {
                    moveX = (width * 2 - expandX) - imageInfo.getX();
                } else if (imageInfo.getX() < (width * 2 - expandX)) {
                    moveX = (width * 2 - expandX) - imageInfo.getX();
                }
                break;
            }
        }


        if (moveX == 0 & i == size) {
            moveX = (width * 2 - expandX) - imageInfoList.get(i - 1).getX();
        }
        return moveX;
    }

    private void needX(int moveX) {
        valueAnimator = ValueAnimator.ofInt(moveX, 0);
        valueAnimator.setEvaluator(new DeceIntEvaluator());
        final int finalMoveX = moveX;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (int) animation.getAnimatedValue();
                int dx;
                if (beforeValue == 0 & value != 0) {
                    dx = (finalMoveX - value);
                } else {
                    dx = (beforeValue - value);
                }
                for (int i = 0; i < size; i++) {
                    if (dx > width) {
                        if (i == 0) {
                            imageInfoList.get(i).setX(imageInfoList.get(i).getX() + width);
                        } else {
                            imageInfoList.get(i).setX(imageInfoList.get(i - 1).getX() + width);
                        }
                    } else if (dx < -width) {
                        if (i == 0) {
                            imageInfoList.get(i).setX(imageInfoList.get(i).getX() - width);
                        } else {
                            imageInfoList.get(i).setX(imageInfoList.get(i - 1).getX() + width);
                        }
                    } else {
                        if (i == 0) {
                            imageInfoList.get(i).setX(imageInfoList.get(i).getX() + dx);
                        } else {
                            imageInfoList.get(i).setX(imageInfoList.get(i - 1).getX() + width);
                        }
                    }
                    imageInfoList.get(i).setIsPressed(false);

                }
                beforeValue = value;
                invalidate();
            }
        });
        float time = (moveX * 1.0f) / Common.screamWidth;
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration((long) Math.abs(time * 1000));
        valueAnimator.start();
    }


    int expandX = 0;
    int[] status;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (width == 0) {
            float temp = (1.50f) / visiCount;
            expandX = (int) (Common.screamWidth * temp / 2);
            width = (Common.screamWidth + expandX * 2) / visiCount;
            height = getHeight() * 2 / 3;
            startDx = width / 5;
            if (status != null) {
                setImageInfoList(status);
            }
        }
    }

    public void setImageInfoList(int[] status) {

        if (width == 0) {
            this.status = status;
            return;
        }
        imageInfoList.clear();
        for (int i = 0; i < size; i++) {
            ImageInfo imageInfo = new ImageInfo();
            if (i == 0) {
                imageInfo.setX(-expandX);
            } else {
                imageInfo.setX(imageInfoList.get(i - 1).getX() + width);
            }

            imageInfo.setStatus(status[i]);

            imageInfo.setTextColor(getResources().getColor(R.color.profile_item_text));
            setResource(imageInfo, i);
            imageInfoList.add(imageInfo);
        }
        currentIndex = visiCount / 2;
        if (textView != null) {
            textView.setText(imageInfoList.get(currentIndex).getDescribe());
        }
        cacheImage = imageInfoList.get(visiCount);
        invalidate();
    }


    private void setResource(ImageInfo imageInfo, int index) {

        if (index == 0) {
            imageInfo.setText("下载中断");
            imageInfo.setDescribe("请在网络环境畅通的情况下下载");
            imageInfo.setBmp((BitmapDrawable) getResources().getDrawable(R.drawable.icon_down_failed));
        } else if (index == 1) {
            imageInfo.setText("下载中");
            imageInfo.setDescribe("请保持网络畅通");
            imageInfo.setBmp((BitmapDrawable) getResources().getDrawable(R.drawable.icon_down_loadding));

        } else if (index == 2) {
            imageInfo.setText("已下载");
            imageInfo.setDescribe("考试必过，加油");
            imageInfo.setBmp((BitmapDrawable) getResources().getDrawable(R.drawable.icon_down_success));

        } else if (index == 3) {
            imageInfo.setText("已上传");
            imageInfo.setDescribe("感谢您的支持");
            imageInfo.setBmp((BitmapDrawable) getResources().getDrawable(R.drawable.icon_upload_success));

        } else if (index == 4) {
            imageInfo.setText("上传中");
            imageInfo.setDescribe("请保持网络畅通");
            imageInfo.setBmp((BitmapDrawable) getResources().getDrawable(R.drawable.icon_uploadding));

        } else if (index == 5) {
            imageInfo.setText("上传中断");
            imageInfo.setDescribe("请在网络环境畅通的情况下下载");
            imageInfo.setBmp((BitmapDrawable) getResources().getDrawable(R.drawable.icon_upload_failed));

        }
    }

    private Bitmap zoomDrawable(BitmapDrawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawable.getBitmap();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (vTracker != null) {
            vTracker.clear();
            vTracker.recycle();
            vTracker = null;
        }

    }

    private void setMiddle() {
        int moveX = 0;
        int max = 0;
        int i = 0;
        for (; i < size; i++) {
            ImageInfo imageInfo = imageInfoList.get(i);
            if (imageInfo.getShowX() >= Common.screamWidth || imageInfo.getShowX() + width <= 0) {
                continue;
            }
            int mw = imageInfo.getWidth(expandX, width);
            if (mw <= 0) {
                continue;
            }
            if (mw > max) {
                max = mw;
            } else {
                imageInfo = imageInfoList.get(i - 1);
                if (imageInfo.getX() > (width * 2 - expandX)) {
                    moveX = (width * 2 - expandX) - imageInfo.getX();
                } else if (imageInfo.getX() < (width * 2 - expandX)) {
                    moveX = (width * 2 - expandX) - imageInfo.getX();
                }
                break;
            }
        }


        if (!isLoop & moveX == 0 & i == size) {
            moveX = (width * 2 - expandX) - imageInfoList.get(i - 1).getX();
        }
        if (textView != null) {
            if (i == size) {
                textView.setText(imageInfoList.get(size - 1).getDescribe());
            } else {
                textView.setText(imageInfoList.get(i - 1).getDescribe());
            }
        }
        currentIndex = i - 1;

        if(circleClick != null){
            circleClick.click(currentIndex);
        }

        if (moveX == 0) {
            return;
        }

        valueAnimator = ValueAnimator.ofInt(moveX, 0);
        valueAnimator.setEvaluator(new DeceIntEvaluator());
        final int finalMoveX = moveX;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (int) animation.getAnimatedValue();
                int dx;
                if (beforeValue == 0 & value != 0) {
                    dx = (finalMoveX - value);
                } else {
                    dx = (beforeValue - value);
                }
                setImageInfoList(dx);
                beforeValue = value;
                invalidate();
            }
        });
        float time = (moveX * 1.0f) / Common.screamWidth;
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration((long) Math.abs(time * 1000));
        valueAnimator.start();


    }

    private void gotoMiddle(final int moveX) {

        if (moveX == 0) {
            return;
        }
        stopAnimation();

        valueAnimator = ValueAnimator.ofInt(moveX, 0);
        valueAnimator.setEvaluator(new DeceIntEvaluator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (int) animation.getAnimatedValue();
                int dx;
                if (beforeValue == 0 && value != 0) {
                    dx = (moveX - value);
                } else {
                    dx = (beforeValue - value);
                }
                setImageInfoList(dx);
                beforeValue = value;
                invalidate();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(circleClick != null){
                    circleClick.click(currentIndex);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        float time = (moveX * 1.0f) / Common.screamWidth;
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration((long) Math.abs(time * 1000));
        valueAnimator.start();
    }

    private void skipMiddle(final int moveX) {

        if (moveX == 0) {
            return;
        }
        stopAnimation();
        valueAnimator = ValueAnimator.ofInt(moveX, 0);
        valueAnimator.setEvaluator(new DeceIntEvaluator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (int) animation.getAnimatedValue();
                int dx;
                if (beforeValue == 0 && value != 0) {
                    dx = (moveX - value);
                } else {
                    dx = (beforeValue - value);
                }
                setImageInfoList(dx);
                beforeValue = value;
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        float time = (moveX * 1.0f) / Common.screamWidth;
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration((long) Math.abs(time * 1000));
        valueAnimator.start();
    }


    CircleClick circleClick;

    @Override
    public void onClick(CircleClick circleClick) {
        this.circleClick = circleClick;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                setMiddle();
            }
            invalidate();

        }
    };
}
