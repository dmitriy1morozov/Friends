package com.gbksoft.android.test.app.main.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

class SpinnerImageArrayAdapter extends ArrayAdapter<Integer> {

  private Integer[] mIcons;
  private int mDropDownHeight;

  SpinnerImageArrayAdapter(@NonNull Context context, Integer[] icons) {
    super(context, android.R.layout.simple_spinner_item, icons);
    this.mIcons = icons;
  }

  @Override public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    if(mDropDownHeight == 0) {
      mDropDownHeight = Math.min(parent.getWidth(), parent.getHeight());
    }
    if(convertView != null) {
      convertView.setBackgroundResource(mIcons[position]);
    } else {
      convertView = getImageView(position);
    }
    return convertView;
  }

  @NonNull @Override public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    if(mDropDownHeight == 0) {
      mDropDownHeight = Math.min(parent.getWidth(), parent.getHeight());
    }
    return getImageView(position);
  }

  private View getImageView(int position) {
    ImageView imageView = new ImageView(getContext());
    imageView.setBackgroundResource(mIcons[position]);
    imageView.setLayoutParams(new AbsListView.LayoutParams(mDropDownHeight, mDropDownHeight));
    return imageView;
  }
}