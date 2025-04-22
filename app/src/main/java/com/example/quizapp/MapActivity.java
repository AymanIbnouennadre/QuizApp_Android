package com.example.quizapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Initialiser le fragment Google Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.id_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialiser le client de localisation
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Bouton Sortir
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(MapActivity.this, Home.class));
            finish();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // Demander les permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // ✅ Activer la localisation sur la carte
        gMap.setMyLocationEnabled(true);

        // ✅ Obtenir la dernière position connue
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                        // Ajouter un marqueur à la position actuelle
                        gMap.addMarker(new MarkerOptions().position(userLatLng).title("Vous êtes ici"));

                        // Centrer la carte sur la position
                        CameraPosition position = new CameraPosition.Builder()
                                .target(userLatLng)
                                .zoom(15)
                                .build();
                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                    } else {
                        Toast.makeText(this, "Impossible d'obtenir la position actuelle", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ✅ Gérer la réponse utilisateur à la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onMapReady(gMap); // Réinitialise la carte avec la position
        } else {
            Toast.makeText(this, "Permission de localisation refusée", Toast.LENGTH_SHORT).show();
        }
    }
}
