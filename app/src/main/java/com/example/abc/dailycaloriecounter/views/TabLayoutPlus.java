package com.example.abc.dailycaloriecounter.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abc.dailycaloriecounter.R;

import java.util.logging.Logger;


public class TabLayoutPlus extends TabLayout {
    Typeface tf;

    public TabLayoutPlus(Context context) {
        super(context);
    }

    public TabLayoutPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context,attrs);
    }

    public TabLayoutPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context,attrs);
    }


    @Override
    public void addTab(Tab tab, boolean setSelected) {
        super.addTab(tab, setSelected);
        if (tf == null) {
            return;
        }
        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());

        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                ((TextView) tabViewChild).setTypeface(tf, Typeface.NORMAL);
            }
        }
    }


    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        String customFont = a.getString(R.styleable.TextViewPlus_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
