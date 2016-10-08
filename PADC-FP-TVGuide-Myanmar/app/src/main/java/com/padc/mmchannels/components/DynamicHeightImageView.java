package com.padc.mmchannels.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by user on 9/10/2016.
 */
public class DynamicHeightImageView extends ImageView {
    private float mAspectRatio = 1.4f;

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (int) (measuredWidth / mAspectRatio));
    }
}
