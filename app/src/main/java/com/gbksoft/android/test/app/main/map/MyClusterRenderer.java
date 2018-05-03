package com.gbksoft.android.test.app.main.map;

import android.content.Context;
import com.gbksoft.android.test.app.Utils;
import com.gbksoft.android.test.app.data.pojo.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class MyClusterRenderer extends DefaultClusterRenderer<CustomCluster> {

  private final Context mContext;
  private User mSelectedUser;
  private GoogleMap mGoogleMap;

  MyClusterRenderer(Context context, GoogleMap map, ClusterManager<CustomCluster> clusterManager) {
    super(context, map, clusterManager);
    mContext = context;
    mGoogleMap = map;
  }

  public void setSelectedUser(User selectedUser) {
    this.mSelectedUser = selectedUser;
    if(selectedUser != null) {
      LatLng position = new LatLng(mSelectedUser.getLat(), mSelectedUser.getLon());
      mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, Utils.DEFAULT_ZOOM));
    }
  }

  @Override protected void onBeforeClusterItemRendered(CustomCluster item, MarkerOptions markerOptions) {
    int picId = item.getUser().getPictureId();
    int drawableResId = mContext.getResources().getIdentifier("icon" + picId, "drawable", mContext.getPackageName());
    BitmapDescriptor bitmapDescriptor = Utils.bitmapDescriptorFromVector(mContext, drawableResId, 32);
    markerOptions.icon(bitmapDescriptor);
  }

  @Override protected void onBeforeClusterRendered(Cluster<CustomCluster> cluster, MarkerOptions markerOptions) {
    int clusterItemsCount = cluster.getSize();
    BitmapDescriptor bitmapDescriptor = Utils.createClusterIcon(mContext, clusterItemsCount);
    markerOptions.icon(bitmapDescriptor);
  }

  @Override protected boolean shouldRenderAsCluster(Cluster<CustomCluster> cluster) {
    return cluster.getSize() >= 3;
  }

  @Override protected void onClusterItemRendered(CustomCluster clusterItem, Marker marker) {
    super.onClusterItemRendered(clusterItem, marker);
    if(mSelectedUser != null && clusterItem.equals(new CustomCluster(mSelectedUser))) {
      marker.showInfoWindow();
      mSelectedUser = null;
    }
  }
}
