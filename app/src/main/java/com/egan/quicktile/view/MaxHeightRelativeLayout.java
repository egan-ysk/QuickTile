package com.egan.quicktile.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.egan.quicktile.R;

/**
 * 支持最大高度的相对布局.
 */
public class MaxHeightRelativeLayout extends RelativeLayout {

    private int maxHeight = -1;

    public MaxHeightRelativeLayout(Context context) {
        this(context, null);
    }

    public MaxHeightRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MaxHeightRelativeLayout, defStyleAttr, 0);
        maxHeight = a.getDimensionPixelSize(R.styleable.MaxHeightRelativeLayout_max_height, maxHeight);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (maxHeight > 0 && heightSize > maxHeight) {
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            heightSize = maxHeight;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}