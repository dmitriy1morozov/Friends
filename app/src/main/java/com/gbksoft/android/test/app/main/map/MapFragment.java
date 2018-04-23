package com.gbksoft.android.test.app.main.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.gbksoft.android.test.app.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

public class MapFragment extends Fragment implements MapContract.View,
    GoogleMap.OnMapLoadedCallback, OnMapReadyCallback,
    GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {

  private static final String TAG = "MyLogs MapFragment";
  public static final String TAB_NAME = "Map";

  private MapView mMapView;
  private GoogleMap mGoogleMap;
  private MapContract.Presenter mPresenter;

  public MapFragment() {
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new MapPresenter();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_map, container, false);
    mMapView = rootView.findViewById(R.id.map_googleMap);

    return rootView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    MapsInitializer.initialize(getActivity());
    mMapView.onCreate(savedInstanceState);
    mMapView.getMapAsync(this);
  }

  @Override public void onStart() {
    Log.d(TAG, "onStart: ");
    super.onStart();
    mMapView.onStart();
    mPresenter.attachView(this);
  }

  @Override public void onResume() {
    Log.d(TAG, "onResume: ");
    super.onResume();
    mMapView.onResume();
  }

  @Override public void onPause() {
    Log.d(TAG, "onPause: ");
    super.onPause();
    mMapView.onPause();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    Log.d(TAG, "onSaveInstanceState: ");
    mMapView.onSaveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override public void onStop() {
    Log.d(TAG, "onStop: ");
    super.onStop();
    mMapView.onStop();
    mPresenter.detachView();
  }

  @Override public void onDestroy() {
    Log.d(TAG, "onDestroy: ");
    super.onDestroy();
    mMapView.onDestroy();
  }

  //================================================================================================
  @Override public void onMapReady(GoogleMap googleMap) {
    mGoogleMap = googleMap;
    mGoogleMap.setOnMapLoadedCallback(this);
  }


  @Override public void onMapLoaded() {
    //Initialized! Handle all map operations here
    mGoogleMap.setOnCameraIdleListener(this);
  }


  @Override public void onCameraIdle() {
    LatLngBounds mapRectangle = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
    mPresenter.mapChanged(mapRectangle);
  }

  @Override public boolean onMarkerClick(Marker marker) {

    return false;
  }

  //================================================================================================
  @Override public void displayError(String error) {
    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
  }
}
