package com.example.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.firebase.database.*;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class map extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private double currentLat = 0.0;
    private double currentLng = 0.0;
    private String key;
    private boolean firstLocationUpdate = true;

    public map() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        createLocationRequest();
        createLocationCallback();

        if (getArguments() != null) {
            key = getArguments().getString("key");
        }
        return view;
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    currentLat = location.getLatitude();
                    currentLng = location.getLongitude();
                    uploadLocationToFirebase(currentLat, currentLng);

                    if (mMap != null && firstLocationUpdate) {
                        LatLng currentLatLng = new LatLng(currentLat, currentLng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15)); // Centrar con zoom
                        firstLocationUpdate = false;
                    }
                }
            }
        };
    }

    private void uploadLocationToFirebase(double lat, double lng) {
        if (key == null) return;
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
        UserLocation location = new UserLocation(lat, lng);
        myRef.child(key).setValue(location);
    }

    private void loadOtherUsersLocations() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mMap == null) return;

                mMap.clear(); // Limpiar marcadores antiguos

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String username = userSnapshot.getKey();

                    if (username == null || username.equals(key))
                        continue; // No mostrar marcador del usuario actual

                    Double lat = userSnapshot.child("lat").getValue(Double.class);
                    Double lng = userSnapshot.child("lng").getValue(Double.class);

                    if (lat != null && lng != null) {
                        LatLng userLatLng = new LatLng(lat, lng);
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(userLatLng)
                                .title(username));
                        if (marker != null) {
                            marker.setTag(username); // Guardamos el username como tag
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseData", "Error al leer datos: " + error.getMessage());
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        mMap.setMyLocationEnabled(true);
        startLocationUpdates();
        loadOtherUsersLocations();
        mMap.setOnMarkerClickListener(marker -> {
            String clickedUsername = (String) marker.getTag();
            if (clickedUsername != null) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .whereEqualTo("username", clickedUsername)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                String uidOtro = document.getId();
                                String miUid = global.getInstance().getUid();

                                db.collection("chatrooms")
                                        .whereArrayContains("users", miUid)
                                        .get()
                                        .addOnSuccessListener(chatroomsSnapshot -> {
                                            for (DocumentSnapshot chatroom : chatroomsSnapshot) {
                                                java.util.List<String> users = (java.util.List<String>) chatroom.get("users");
                                                if (users != null && users.contains(uidOtro)) {
                                                    String chatroomId = chatroom.getId();
                                                    abrirChat(chatroomId, clickedUsername);
                                                    return;
                                                }
                                            }
                                            java.util.HashMap<String, Object> nuevoChatroom = new java.util.HashMap<>();
                                            nuevoChatroom.put("users", java.util.Arrays.asList(miUid, uidOtro));
                                            nuevoChatroom.put("ultimoMensaje", "");
                                            nuevoChatroom.put("timestamp", com.google.firebase.Timestamp.now());

                                            db.collection("chatrooms")
                                                    .add(nuevoChatroom)
                                                    .addOnSuccessListener(newChatroomRef -> {
                                                        abrirChat(newChatroomRef.getId(), clickedUsername);
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e("Firestore", "Error al crear nuevo chatroom", e);
                                                    });

                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e("Firestore", "Error al buscar chatrooms", e);
                                        });
                            } else {
                                Log.w("Firestore", "No se encontró usuario con nombre: " + clickedUsername);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Error consultando Firestore", e);
                        });
            }
            return false;
        });

    }

    private void abrirChat(String chatroomId, String nombreUsuario) {
        Intent intent = new Intent(requireContext(), ChatRoomActivity.class);
        intent.putExtra("documentId", chatroomId);
        intent.putExtra("nombre", nombreUsuario);
        startActivity(intent);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public static class UserLocation {
        private double lat;
        private double lng;

        public UserLocation() {
        }

        public UserLocation(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}