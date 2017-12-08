package com.karen.ubercloneapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewRequestsActivity extends AppCompatActivity {

    private static final String TAG = ViewRequestsActivity.class.getSimpleName();
    ListView listView;
    List<String> requests;
    ArrayAdapter<String> arrayAdapter;
    LocationManager locationManager;
    LocationListener locationListener;
    List<LatLng> requestLocation;
    List<String> userList;


    public void updateListView(Location location) {

        final ParseGeoPoint geoPointLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.whereNear("location", geoPointLocation).whereDoesNotExist("driverUsername").setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    requests.clear();
                    requestLocation.clear();

                    for (ParseObject o : objects) {

                        ParseGeoPoint geoLocation = (ParseGeoPoint) o.get("location");
                        double distanceInMiles = Math.round(geoPointLocation.distanceInMilesTo(geoLocation) * 10) / 10;
                        requests.add(String.valueOf(distanceInMiles) + " miles");
                        requestLocation.add(new LatLng(geoLocation.getLatitude(), geoLocation.getLongitude()));
                        userList.add(o.getString("username"));
                    }

                } else {
                    requests.add("No active requests nearby");
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);
        initView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (ActivityCompat.checkSelfPermission(ViewRequestsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (requestLocation.size() > position && lastKnownLocation != null) {
                        Intent intent = new Intent(ViewRequestsActivity.this, DriverActivity.class);
                        intent.putExtra("requestLocation", requestLocation.get(position));
                        intent.putExtra("driverLocation", new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
                        intent.putExtra("username", userList.get(position));
                        startActivity(intent);
                    }
                }
            }
        });

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateListView(location);
                ParseUser.getCurrentUser().put("location", new ParseGeoPoint(location.getLatitude(), location.getLongitude()));
                ParseUser.getCurrentUser().saveInBackground();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                updateListView(lastKnownLocation);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                checkPermissionLocation();
            }
        }
    }

    private void checkPermissionLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                updateListView(lastKnownLocation);
            }
        }
    }

    private void initView() {
        requestLocation = new ArrayList<>();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        listView = findViewById(R.id.requestListView);
        requests = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requests);

        requests.clear();
        requests.add("Getting nearby requests...");

        listView.setAdapter(arrayAdapter);

        userList = new ArrayList<>();
    }
}
