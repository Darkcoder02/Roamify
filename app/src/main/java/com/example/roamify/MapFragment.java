package com.example.roamify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.roamify.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.Nullable;


public class MapFragment extends Fragment implements OnMapReadyCallback{

private GoogleMap mMap;
    private FragmentMapBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return binding.getRoot();
    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        LatLng sydney = new LatLng(-34, 151);
//        if (mMap != null) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        }
//
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            double latitude = extras.getDouble("latitude");
            double longitude = extras.getDouble("longitude");

            // Add marker and move camera

            LatLng location = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(location).title(name));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
        } else {
            Toast.makeText(getActivity(), "Error: Location data not found.", Toast.LENGTH_SHORT).show();
        }
//        mMap = googleMap;

//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}