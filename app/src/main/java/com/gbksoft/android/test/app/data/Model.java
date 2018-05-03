package com.gbksoft.android.test.app.data;

import android.util.Log;
import com.gbksoft.android.test.app.data.pojo.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Model implements DataContract.Model, ChildEventListener {

  private static final String TAG = "MyLogs Model";

  private static final String TABLE_NAME = "users";

  private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
  private DataContract.Presenter mPresenter;

  @Override public void attachPresenter(DataContract.Presenter presenter) {
    mPresenter = presenter;
    mRootRef.child(TABLE_NAME).addChildEventListener(this);
  }

  @Override public void detachPresenter(DataContract.Presenter presenter) {
    mPresenter = null;
    mRootRef.child(TABLE_NAME).removeEventListener(this);
    mRootRef = null;
  }

  @Override public void addUser(User user) {
    Log.d(TAG, "addUser: ");
    mRootRef.child(TABLE_NAME).push().setValue(user);
  }

  @Override public void removeUser(String id) {
    mRootRef.child(TABLE_NAME).child(id).removeValue();
  }

  //====================================================================================================================
  @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
    Log.d(TAG, "onChildAdded: ");
    String uid = dataSnapshot.getKey();
    User newUser = dataSnapshot.getValue(User.class);
    newUser.setUid(uid);
    if(mPresenter != null) {
      mPresenter.dispatchUser(newUser);
    }
  }

  @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    Log.d(TAG, "onChildChanged: ");
  }

  @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
    Log.d(TAG, "onChildRemoved:");
    String uid = dataSnapshot.getKey();
    if(mPresenter != null) {
      mPresenter.userRemoved(uid);
    }
  }

  @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    Log.d(TAG, "onChildMoved: ");
  }

  @Override public void onCancelled(DatabaseError databaseError) {
    Log.d(TAG, "onCancelled: ");
  }
}
