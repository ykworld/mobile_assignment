package com.yk.mobileassignment.ui.map;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yk.mobileassignment.constants.Constants;
import com.yk.mobileassignment.model.Coord;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap map;
    private Coord coord;

    @Override
    public void setArguments(Bundle bundle) {
        coord = (Coord) bundle.getSerializable(Constants.BUNDLE_COORD);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng latLng = new LatLng(coord.getLat(), coord.getLon());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(8));
    }
}
