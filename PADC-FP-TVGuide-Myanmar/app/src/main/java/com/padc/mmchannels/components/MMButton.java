package com.padc.mmchannels.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.padc.mmchannels.utils.MMFontUtils;

/**
 * Created by user on 9/10/2016.
 */
public class MMButton extends Button {

    public MMButton(Context context) {
        super(context);
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }

    public MMButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }

    public MMButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }
}
