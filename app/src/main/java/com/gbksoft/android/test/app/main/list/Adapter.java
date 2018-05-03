package com.gbksoft.android.test.app.main.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gbksoft.android.test.app.R;
import com.gbksoft.android.test.app.data.pojo.User;
import java.util.ArrayList;

class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private Context mContext;
  private ArrayList<User> mUsers;
  private Holder.OnGoToMapClickListener mOnGoToMapClickListener;
  private Holder.OnRemoveUserListener mOnRemoveUserListener;

  Adapter(Context context, ArrayList<User> users, Holder.OnGoToMapClickListener onGoToMapClickListener,
      Holder.OnRemoveUserListener onRemoveUserListener) {
    mContext = context;
    mUsers = users;
    mOnGoToMapClickListener = onGoToMapClickListener;
    mOnRemoveUserListener = onRemoveUserListener;
  }

  @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View itemView = inflater.inflate(R.layout.viewholder, parent, false);
    return new Holder(itemView);
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((Holder) holder).bind(mUsers.get(position), mOnGoToMapClickListener, mOnRemoveUserListener);
  }

  @Override public int getItemCount() {
    return mUsers.size();
  }
}