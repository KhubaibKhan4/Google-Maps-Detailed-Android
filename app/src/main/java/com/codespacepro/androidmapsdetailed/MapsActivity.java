package com.codespacepro.androidmapsdetailed;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.codespacepro.androidmapsdetailed.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(23.234, 43.234);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Codespacepro"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
        mMap.addCircle(new CircleOptions().center(latLng).strokeColor(Color.BLUE).fillColor(Color.BLUE));
        mMap.addPolygon(new PolygonOptions().add(new LatLng(23.234, 25.2344),
                new LatLng(26.234, 29.234),
                new LatLng(29.234, 30.234),
                new LatLng(34.234, 39.123)));
        mMap.addGroundOverlay(new GroundOverlayOptions().position(latLng, 1000f, 100f).image(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background)));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Clicked Here."));
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try {
                    ArrayList<Address> arrayList = (ArrayList<Address>) geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Toast.makeText(MapsActivity.this, "" + arrayList.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}