package com.gbksoft.android.test.app.main;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

  private Boolean mIsScrollEnabled = false;

  public MyViewPager(Context context) {
    super(context);
  }

  public MyViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    if(mIsScrollEnabled) {
      return super.onInterceptTouchEvent(event);
    } else {
      return false;
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if(mIsScrollEnabled) {
      return super.onTouchEvent(event);
    } else {
      return false;
    }
  }
}