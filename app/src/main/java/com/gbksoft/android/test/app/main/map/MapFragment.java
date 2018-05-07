package com.gbksoft.android.test.app.main.map;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.gbksoft.android.test.app.R;
import com.gbksoft.android.test.app.Utils;
import com.gbksoft.android.test.app.data.pojo.User;
import com.gbksoft.android.test.app.main.UserSelectedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import java.lang.ref.WeakReference;

public class MapFragment extends Fragment
    implements MapContract.View, UserSelectedListener,
    GoogleMap.OnMapLoadedCallback, OnMapReadyCallback,
    GoogleMap.OnCameraIdleListener, GoogleMap.OnMapLongClickListener,
    ClusterManager.OnClusterClickListener<CustomCluster>, ClusterManager.OnClusterItemClickListener<CustomCluster> {

  public static final String TAB_NAME = "Map";
  private static final String TAG = "MyLogs MapFragment";
  private static final int RC_LOCATION_PERMISSIONS = 1;
  private static final LatLng DEFAULT_LOCATION = new LatLng(50.458843, 30.517561);

  private MapView mMapView;
  private GoogleMap mGoogleMap;
  private FusedLocationProviderClient mFusedLocationClient;
  private ClusterManager<CustomCluster> mClusterManager;
  private MyClusterRenderer mRenderer;
  private User mSelectedUser;
  private Marker mLastClickedMarker;

  private MapContract.Presenter mPresenter;

  public MapFragment() {
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate: ");
    super.onCreate(savedInstanceState);
    mPresenter = new MapPresenter();
    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView: ");
    View rootView = inflater.inflate(R.layout.fragment_map, container, false);
    mMapView = rootView.findViewById(R.id.map_googleMap);
    return rootView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onActivityCreated: ");
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
    moveToDevicePosition();
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

  @Override public void onSaveInstanceState(@NonNull Bundle outState) {
    Log.d(TAG, "onSaveInstanceState: ");
    mMapView.onSaveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override public void onStop() {
    Log.d(TAG, "onStop: ");
    super.onStop();
    mMapView.onStop();
    mSelectedUser = null;
    mRenderer = null;
    mPresenter.detachView();
  }

  @Override public void onDestroy() {
    Log.d(TAG, "onDestroy: ");
    super.onDestroy();
    mMapView.onDestroy();
  }

  //================================================================================================
  @Override public void onMapReady(GoogleMap googleMap) {
    Log.d(TAG, "onMapReady: ");
    mGoogleMap = googleMap;
    mGoogleMap.setOnMapLoadedCallback(this);
  }

  @Override public void onMapLoaded() {
    Log.d(TAG, "onMapLoaded: ");
    mClusterManager = new ClusterManager<>(getActivity(), mGoogleMap);
    mRenderer = new MyClusterRenderer(getActivity(), mGoogleMap, mClusterManager);
    mClusterManager.setRenderer(mRenderer);
    mClusterManager.setOnClusterClickListener(MapFragment.this);
    mClusterManager.setOnClusterItemClickListener(MapFragment.this);

    mGoogleMap.setOnMarkerClickListener(mClusterManager);
    mGoogleMap.setOnCameraIdleListener(this);
    mGoogleMap.setOnMapLongClickListener(this);
    enableGpsButton();
    if(mSelectedUser != null) {
      mRenderer.setSelectedUser(mSelectedUser);
    } else {
      onCameraIdle();
    }
  }

  @Override public void onCameraIdle() {
    Log.d(TAG, "onCameraIdle: ");
    LatLngBounds mapRectangle = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
    mPresenter.mapChanged(mapRectangle);
    mClusterManager.onCameraIdle();
  }

  @Override public boolean onClusterClick(Cluster<CustomCluster> cluster) {
    Log.d(TAG, "onClusterClick: ");
    LatLngBounds.Builder builder = LatLngBounds.builder();
    for(ClusterItem item : cluster.getItems()) {
      builder.include(item.getPosition());
    }
    final LatLngBounds bounds = builder.build();
    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    return true;
  }

  @Override public boolean onClusterItemClick(CustomCluster customCluster) {
    Log.d(TAG, "onClusterItemClick: ");
    boolean doNotMoveCameraToMarker = true;
    Marker marker = mRenderer.getMarker(customCluster);
    if(mLastClickedMarker != null && mLastClickedMarker.equals(marker)) {
      mLastClickedMarker = null;
      marker.hideInfoWindow();
    } else {
      mLastClickedMarker = marker;
      marker.showInfoWindow();
    }
    return doNotMoveCameraToMarker;
  }

  @Override public void onMapLongClick(LatLng latLng) {
    Log.d(TAG, "onMapLongClick: ");
    LayoutInflater inflater = getLayoutInflater();
    View dialogView = inflater.inflate(R.layout.dialog_map_add_user, null);
    Spinner iconSpinner = dialogView.findViewById(R.id.spinner_dialog_icon);
    EditText nameText = dialogView.findViewById(R.id.text_dialog_name);

    Integer[] icons = getIcons();
    SpinnerImageArrayAdapter spinnerAdapter = new SpinnerImageArrayAdapter(getActivity(), icons);
    iconSpinner.setAdapter(spinnerAdapter);

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(getActivity().getResources().getString(R.string.map_dialog_new_marker));
    builder.setView(dialogView);
    builder.setPositiveButton(R.string.map_save_marker, (dialog, which) -> {
      User user = new User();
      user.setName(nameText.getText().toString());
      user.setLat(latLng.latitude);
      user.setLon(latLng.longitude);
      user.setPictureId(iconSpinner.getSelectedItemPosition());
      mPresenter.addUser(user);
    });
    builder.create().show();
    // Bugfix. This is required to invalidate first element in the spinner.
    // In this place spinner view is invalidated so the measurements are known.
    spinnerAdapter.notifyDataSetChanged();
  }

  //================================================================================================
  @Override public void displayError(String error) {
    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
  }

  @Override public void dispatchUser(User user) {
    Log.d(TAG, "dispatchUser: " + user.toString());
    CustomCluster newCluster = new CustomCluster(user);
    mClusterManager.addItem(newCluster);
    mClusterManager.cluster();
  }

  @Override public void removeUser(String uid) {
    Log.d(TAG, "userRemoved: ");
    User removingUser = new User(uid);
    if(mClusterManager != null) {
      mClusterManager.removeItem(new CustomCluster(removingUser));
      mClusterManager.cluster();
    }
  }

  @Override public void removeMarkers() {
    Log.d(TAG, "removeMarkers: ");
    mGoogleMap.clear();
    mClusterManager.clearItems();
  }

  @Override public void selectUser(User user) {
    Log.d(TAG, "selectUser: ");
    // if (mRenderer != null) then the mGoogleMap is already loaded. We may proceed to marker selection
    if(mRenderer != null) {
      mRenderer.setSelectedUser(user);
    } else {
      mSelectedUser = user;
    }
  }

  //================================================================================================
  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    Log.d(TAG, "onRequestPermissionsResult: ");
    switch(requestCode) {
      case RC_LOCATION_PERMISSIONS:
        if(grantResults.length == 0) {
          return;
        }
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          Log.d(TAG, "onRequestPermissionsResult: Permissions granted");
          moveToDevicePosition();
        } else {
          Snackbar.make(mMapView, getString(R.string.map_permissions_required),
              Snackbar.LENGTH_LONG).setAction("Grant", v -> launchAppSettingsActivity()).show();
        }
        break;
      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  private void enableGpsButton() {
    int hasCoarseLocationPermissions =
        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
    int hasFineLocationPermissions =
        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        && hasCoarseLocationPermissions != PackageManager.PERMISSION_GRANTED
        && hasFineLocationPermissions != PackageManager.PERMISSION_GRANTED) {
      String requestString[] = { Manifest.permission.ACCESS_FINE_LOCATION };
      requestPermissions(requestString, RC_LOCATION_PERMISSIONS);
    } else {
      mGoogleMap.setMyLocationEnabled(true);
    }
  }

  private void moveToDevicePosition() {
    int hasCoarseLocationPermissions =
        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
    int hasFineLocationPermissions =
        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        && hasCoarseLocationPermissions != PackageManager.PERMISSION_GRANTED
        && hasFineLocationPermissions != PackageManager.PERMISSION_GRANTED) {
      String requestString[] = { Manifest.permission.ACCESS_FINE_LOCATION };
      requestPermissions(requestString, RC_LOCATION_PERMISSIONS);
    } else {
      WeakReference<Context> contextRef = new WeakReference<>(getActivity());
      mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
        LatLng deviceLatLng = DEFAULT_LOCATION;
        if(task.isSuccessful() && task.getResult() != null) {
          Location location = task.getResult();
          deviceLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        } else if(contextRef.get() != null) {
          Context context = contextRef.get();
          Toast.makeText(context, "Couldn't detect your location. Moving to default point", Toast.LENGTH_SHORT).show();
        }
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deviceLatLng, Utils.DEFAULT_ZOOM));
      });
    }
  }

  private void launchAppSettingsActivity() {
    Intent permissionsIntent = new Intent();
    permissionsIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
    permissionsIntent.setData(uri);
    permissionsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    permissionsIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    startActivity(permissionsIntent);
  }

  private Integer[] getIcons() {
    Integer[] icons = new Integer[100];
    for(int i = 0; i < 100; i++) {
      icons[i] = getResources().getIdentifier("icon" + i, "drawable", getActivity().getPackageName());
    }
    return icons;
  }
}