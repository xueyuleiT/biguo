/*
 * SwitchChoice.java
 * classes : com.example.myview.SwitchChoice
 * @author zenghui
 * V 1.0.0
 * Create at 2015-10-17 上午9:42:27
 */
package com.example.zenghui.overyearspaper.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.zenghui.overyearspaper.Listener.CheckListener;
import com.example.zenghui.overyearspaper.Listener.OnCheckListener;
import com.example.zenghui.overyearspapers.R;


/**
 * com.example.myview.SwitchChoice
 * 
 * @author zenghui <br/>
 *         create at 2015-10-17 上午9:42:27
 */
public class SwitchChoice extends View implements CheckListener {

    private Paint cyclePaint, bgPaint, innerPaint;
    Path bgPath;
    int smallR, largeR;
    boolean checked = false;
    boolean isLeft = true;

    public SwitchChoice(Context context, AttributeSet attrs, boolean left) {
        this(context, attrs);
        this.isLeft = left;
    }

    public SwitchChoice(Context context, AttributeSet attrs) {
        super(context, attrs);

        bgPaint = new Paint();
        bgPaint.setColor(getResources().getColor(R.color.top_title_blue_theme));
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Style.FILL);

        cyclePaint = new Paint();
        cyclePaint.setColor(getResources().getColor(R.color.main_bg));
        cyclePaint.setStyle(Style.FILL);
        cyclePaint.setAntiAlias(true);

        innerPaint = new Paint();
        innerPaint.setColor(getResources().getColor(R.color.gray_line));
        innerPaint.setStyle(Style.FILL);
        innerPaint.setAntiAlias(true);

    }

    int currentX, currentY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getX() > 0 && event.getX() < getWidth()) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // if (!isInCycle(event.getX(), event.getY())) {
                    // return true;
                    // }
                    currentX = (int) event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getX() <= getWidth() - smallR - 5 && event.getX() >= 5 + smallR) {
                        currentX = (int) event.getX();
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (currentX > getWidth() / 2) {
                        currentX = getWidth() - smallR - 5;
                        if (listener != null)
                            listener.sumbitCheck();
                    } else {
                        currentX = smallR + 5;
                        if (listener != null)
                            listener.clearCheck();
                    }
                    invalidate();
                    break;
                default:
                    break;
            }
        } else {
            if (currentX > getWidth() / 2) {
                currentX = getWidth() - smallR - 5;
            } else {
                currentX = smallR + 5;
            }
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(bgPath, bgPaint);
        if (currentX + 15 + smallR <= getWidth()) {
            innerPath.reset();
            innerPath.moveTo(currentX, getHeight() * 5 / 6 - 3);
            innerPath.lineTo(currentX, getHeight() / 6 + 3);
            innerPath.lineTo(getWidth() - smallR - 3, getHeight() / 6 - 2);
            innerPath.arcTo(new RectF(getWidth() - 2 * largeR - 3, getHeight() / 6 - 2, getWidth() - 3,
                    getHeight() * 5 / 6 + 2), 270, 180);
            innerPath.close();
            canvas.drawPath(innerPath, innerPaint);
        }
        canvas.drawCircle(currentX, currentY, smallR, cyclePaint);
    }

    Path innerPath;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (bgPath == null) {
            bgPath = new Path();
            innerPath = new Path();
            smallR = getHeight() * 2 / 6;
            largeR = smallR + 5;
            bgPath.moveTo(smallR, getHeight() * 5 / 6);
            RectF oval = new RectF(); // RectF对象
            oval.left = 0; // 左边
            oval.top = getHeight() / 6; // 上边
            oval.right = smallR * 2; // 右边
            oval.bottom = getHeight() * 5 / 6; // 下边
            bgPath.arcTo(oval, 90, 180);

            RectF ovalLarge = new RectF(); // RectF对象
            ovalLarge.left = getWidth() - 2 * largeR; // 左边
            ovalLarge.top = getHeight() / 6 - 5; // 上边
            ovalLarge.right = getWidth(); // 右边
            ovalLarge.bottom = getHeight() * 5 / 6 + 5; // 下边
            bgPath.lineTo(getWidth() - largeR, ovalLarge.top);
            bgPath.arcTo(ovalLarge, 270, 180);
            bgPath.lineTo(smallR, oval.bottom);

            innerPath.moveTo(smallR, getHeight() * 5 / 6 - 5);
            innerPath.lineTo(smallR, getHeight() / 6 + 5);
            innerPath.lineTo(getWidth() - smallR - 3, getHeight() / 6 - 2);
            innerPath.arcTo(
                    new RectF(ovalLarge.left - 3, ovalLarge.top + 3, ovalLarge.right - 3, ovalLarge.bottom - 3), 270,
                    180);
            innerPath.close();
            smallR -= 3;
            if (isLeft) {
                currentX = smallR + 5;
            } else {
                currentX = getWidth() - smallR - 5;
            }
            currentY = getHeight() / 2;
        }

    }

    boolean isInCycle(float f, float g) {
        return Math.sqrt((f - currentX) * (f - currentX) + (g - currentY) * (g - currentY)) <= smallR ? true : false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    OnCheckListener listener;

    @Override
    public void setOnCheckListener(OnCheckListener listener) {
        this.listener = listener;
    }

    /**
     * @return the currentX
     */
    public int getCurrentX() {
        return this.currentX;
    }

    public void setRight() {
        this.currentX = getWidth() - smallR - 5;
        postInvalidate();
    }

    public void setLeft() {
        this.currentX = smallR + 5;
        postInvalidate();
    }

    public boolean isChecked() {
        checked = currentX > getWidth() / 2 ? true : false;
        return this.checked;
    }

    public void setChecked(boolean checked) {
        if (checked) {
            setRight();
        } else {
            setLeft();
        }
        this.checked = checked;
    }

}
