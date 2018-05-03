package com.gbksoft.android.test.app.main.list;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.gbksoft.android.test.app.R;
import com.gbksoft.android.test.app.data.pojo.User;
import com.gbksoft.android.test.app.main.UserSelectedListener;
import java.util.ArrayList;

public class ListFragment extends Fragment implements ListContract.View, Holder.OnGoToMapClickListener,
    Holder.OnRemoveUserListener {

  public static final String TAB_NAME = "List";
  private static final String TAG = "MyLogs ListFragment";

  @BindView(R.id.recycler_list) RecyclerView mRecyclerView;
  @BindView(R.id.group_list_no_data) Group mNoDataView;
  private Unbinder mUnbinder;

  private ArrayList<User> mUsers;
  private ListContract.Presenter mPresenter;
  private UserSelectedListener mUserSelectedListener;

  public ListFragment() {
  }

  @Override public void onAttach(Context context) {
    Log.d(TAG, "onAttach: ");
    super.onAttach(context);
    if(context instanceof UserSelectedListener) {
      mUserSelectedListener = (UserSelectedListener) context;
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView: ");
    View rootView = inflater.inflate(R.layout.fragment_list, container, false);
    mUnbinder = ButterKnife.bind(this, rootView);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(linearLayoutManager);
    mUsers = new ArrayList<>();
    Adapter adapter = new Adapter(getActivity(), mUsers, this, this);
    mRecyclerView.setAdapter(adapter);

    DisplayMetrics metrics = getResources().getDisplayMetrics();
    final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, metrics);
    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
      @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
          RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
      }
    });
    return rootView;
  }

  @Override public void onStart() {
    Log.d(TAG, "onStart: ");
    super.onStart();
    mPresenter = new ListPresenter(mUsers);
    mPresenter.attachView(this);

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
          RecyclerView.ViewHolder target) {
        Toast.makeText(getActivity(), "MOVE", Toast.LENGTH_SHORT).show();
        return false;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        String uid = ((Holder) viewHolder).getUser().getUid();
        mPresenter.removeUser(uid);
      }
    };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
    itemTouchHelper.attachToRecyclerView(mRecyclerView);
  }

  @Override public void onStop() {
    Log.d(TAG, "onStop: ");
    super.onStop();
    mPresenter.detachView();
  }

  @Override public void onDestroyView() {
    Log.d(TAG, "onDestroyView: ");
    super.onDestroyView();
    mUnbinder.unbind();
  }

  //================================================================================================
  @Override public void goToMap(Holder item) {
    Log.d(TAG, "goToMap: user = " + item.getUser().getName());
    if(mUserSelectedListener != null) {
      mUserSelectedListener.selectUser(item.getUser());
    } else {
      Log.d(TAG, "goToMap: Error. ListFragment.mUserSelectedListener is null");
    }
  }

  @Override public void removeUser(String id) {
    mPresenter.removeUser(id);
  }

  //================================================================================================
  @Override public void displayError(String error) {
    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
  }

  @Override public void refreshList() {
    mRecyclerView.getAdapter().notifyDataSetChanged();
  }

  @Override public void displayNoDataStub() {
    mRecyclerView.setVisibility(View.GONE);
    mNoDataView.setVisibility(View.VISIBLE);
  }

  @Override public void displayData() {
    mRecyclerView.setVisibility(View.VISIBLE);
    mNoDataView.setVisibility(View.GONE);
  }
}