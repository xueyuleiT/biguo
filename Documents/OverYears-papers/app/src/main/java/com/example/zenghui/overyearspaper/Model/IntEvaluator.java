package com.example.zenghui.overyearspaper.Model;

import android.animation.TypeEvaluator;

/**
 * Created by zenghui on 15/11/28.
 */
public class IntEvaluator implements TypeEvaluator {

    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startFloat = ((Number) startValue).intValue();
        return (int)(startFloat + fraction * (((Number) endValue).intValue() - startFloat));
    }

}
