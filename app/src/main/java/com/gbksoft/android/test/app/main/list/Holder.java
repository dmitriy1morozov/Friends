package com.gbksoft.android.test.app.main.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.gbksoft.android.test.app.R;
import com.gbksoft.android.test.app.data.pojo.User;

class Holder extends RecyclerView.ViewHolder {

  interface OnGoToMapClickListener {
    void goToMap(Holder holder);
  }

  interface OnRemoveUserListener {
    void removeUser(String id);
  }

  private User mUser;
  private TextView mNameText;
  private TextView mLatText;
  private TextView mLonText;
  private Button mMapButton;

  Holder(View itemView) {
    super(itemView);
    itemView.setLayoutParams(
        new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    mNameText = itemView.findViewById(R.id.text_holder_name);
    mLatText = itemView.findViewById(R.id.text_holder_lat);
    mLonText = itemView.findViewById(R.id.text_holder_lon);
    mMapButton = itemView.findViewById(R.id.button_holder_show_on_map);
  }

  public User getUser() {
    return mUser;
  }

  public void bind(User user, OnGoToMapClickListener onGoToMapClickListener,
      OnRemoveUserListener onRemoveUserListener) {
    mUser = user;
    mNameText.setText(mUser.getName());
    mLatText.setText(String.valueOf(mUser.getLat()));
    mLonText.setText(String.valueOf(mUser.getLon()));
    mMapButton.setOnClickListener(v -> onGoToMapClickListener.goToMap(Holder.this));
    itemView.setOnLongClickListener(v -> {
      onRemoveUserListener.removeUser(Holder.this.getUser().getUid());
      return false;
    });
  }
}
