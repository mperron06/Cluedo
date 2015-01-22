package com.polytech.custom.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SquareLayout extends LinearLayout {

	public SquareLayout(Context context) {
		super(context);
	}
	
	public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding < heigthWithoutPadding) {
            width = heigthWithoutPadding + getPaddingLeft() + getPaddingRight();
        } else {
            height = widthWithoutPadding + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

}
