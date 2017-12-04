package com.karen.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView addressTextView;
    private TextView latitude;
    private TextView longitude;
    private TextView altitude;
    private TextView accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        locationSetUp();
        locationPermissions();
    }

    private void locationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // we have permission!
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {

                updateLocation(location);

            }

        }
    }

    private void locationSetUp() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                updateLocation(location);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }

        };
    }

    private void updateLocation(Location location) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.US);
        String address = "\n";
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses != null && addresses.size() > 0) {
                address += addresses.get(0).getAddressLine(0) + "\n";
                address += addresses.get(0).getAddressLine(1) + "\n";
                address += addresses.get(0).getAddressLine(2) + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        latitude.setText(String.format("Latitude: %s", location.getLatitude()));
        longitude.setText(String.format("Longitude: %s", location.getLongitude()));
        accuracy.setText(String.format("Accuracy: %s", location.getAccuracy()));
        altitude.setText(String.format("Altitude: %s", location.getAltitude()));
        addressTextView.setText(String.format("Address: %s", address));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    private void initViews() {
        addressTextView = findViewById(R.id.addressTextView);
        latitude = findViewById(R.id.latitudeTextView);
        longitude = findViewById(R.id.longitudeTextView);
        altitude = findViewById(R.id.altitudeTextView);
        accuracy = findViewById(R.id.accuracyTextView);
    }
}
