package com.example.zenghui.overyearspaper.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.zenghui.overyearspapers.R;


/**
 * Created by zenghui on 15/12/31.
 */
public class ShadowView extends Button {
    public ShadowView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

    }

    public ShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    int l = 10, r = 10, t = 10, b = 10, shadow = 10;


    ValueAnimator valueAnimator;

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        if (pressed) {
            valueAnimator.cancel();
            valueAnimator.start();
        }

        invalidate();

    }

    int width = 0, height = 0;
    int marginLeft, marginRight;
    int color, shadowValue, corner,shadowColor;

    @Override
    protected void onDraw(Canvas canvas) {
        if (resource == 0) {

            if (width == 0) {
                width = getWidth();
                height = getHeight();
                corner = getResources().getDimensionPixelSize(R.dimen.corner_radius);
                marginLeft = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
                marginRight = marginLeft;
                shadowColor = Color.rgb(150,150,150);
                color = getResources().getColor(R.color.colorPrimary);
                valueAnimator = ValueAnimator.ofInt(1, shadow / 2);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        shadowValue = (int) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                valueAnimator.setDuration(100);
            }

            if (width == 0) {
                return;
            }
            if (isPressed()) {
                Paint paint1 = new Paint();
                paint1.setShadowLayer(shadow * 1.5f, 0, shadowValue, shadowColor);
                paint1.setColor(color);
                canvas.drawRoundRect(new RectF(marginLeft + shadow*5/4 - shadowValue*2, t + shadow - shadowValue, getWidth() - marginLeft - shadow*5/4 + shadowValue*2, getHeight() - b * 2 - 1), corner, corner, paint1);

            } else {

                Paint paint1 = new Paint();
                paint1.setShadowLayer(shadow * 1.5f, 0, shadow / 6, shadowColor);
                paint1.setColor(color);
                canvas.drawRoundRect(new RectF(marginLeft + shadow * 3 / 4, t + shadow, getWidth() - marginLeft - shadow * 3 / 4, getHeight() - b * 2 - 1), corner, corner, paint1);

            }
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setAntiAlias(true);
            RectF rectF = new RectF(marginLeft, t, getWidth() - marginLeft, getHeight() - b * 2);
            canvas.drawRoundRect(rectF, corner, corner, paint);
            shadowValue = shadow / 6;

        }
        super.onDraw(canvas);
    }

    int resource = 0;

    public void setmResource(int resource) {
        this.resource = resource;
    }

    @Override
    protected void onDetachedFromWindow() {
        if(valueAnimator != null){
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
            valueAnimator = null;
        }
        super.onDetachedFromWindow();
    }
}
