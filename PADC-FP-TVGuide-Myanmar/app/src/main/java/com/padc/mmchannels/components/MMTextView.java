package com.padc.mmchannels.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.padc.mmchannels.utils.MMFontUtils;

/**
 * Created by user on 9/10/2016.
 */
public class MMTextView extends TextView {

    public MMTextView(Context context) {
        super(context);
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }

    public MMTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }

    public MMTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }
}
