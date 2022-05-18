package com.zjx.customview.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.zjx.customview.R;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 圆角背景的TextView
 */
public class FilletTextView extends AppCompatTextView {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float cornerRadius;
    private int solidColor = Color.TRANSPARENT;

    public FilletTextView(@NonNull @NotNull Context context) {
        super(context);
        initAttributeSet(null);
    }

    public FilletTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initAttributeSet(attrs);
    }

    public FilletTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(attrs);
    }

    @SuppressLint("ResourceType")
    private void initAttributeSet(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.FilletTextView);
            cornerRadius = ta.getInteger(R.attr.radius, 0);
            solidColor = ta.getColor(R.attr.solidColor, Color.TRANSPARENT);
            ta.recycle();
            init();
        }
    }


    private void init() {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(cornerRadius);
        gd.setColor(solidColor);
        setBackgroundDrawable(gd);
    }

}
