package com.karen.ubercloneapp;

import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class DriverActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Intent intent;
    LatLng driverLocation;
    LatLng requestLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        RelativeLayout mapLayout = findViewById(R.id.activity_driver);

        mapLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                intent = getIntent();

                driverLocation = intent.getParcelableExtra("driverLocation");

                requestLocation = intent.getParcelableExtra("requestLocation");

                List<Marker> markers = new ArrayList<>();

                markers.add(mMap.addMarker(new MarkerOptions().position(driverLocation).title("Your location")));
                markers.add(
                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(requestLocation)
                                        .title("Request location")
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        )
                );

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }

                LatLngBounds bounds = builder.build();

                Point displaySizePx = new Point();
                Display display = getWindowManager().getDefaultDisplay();
                display.getSize(displaySizePx);
                Log.i("DisplaySize", displaySizePx.toString());
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, displaySizePx.x, displaySizePx.y, 200);
                mMap.moveCamera(cu);

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    public void acceptRequest(View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.whereEqualTo("username", intent.getStringExtra("username"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject o : objects) {
                        o.put("driverUsername", ParseUser.getCurrentUser().getUsername());
                        o.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    String direction = String.format("http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",
                                            driverLocation.latitude, driverLocation.longitude, requestLocation.latitude, requestLocation.longitude);
                                    Intent directionIntent = new Intent(android.content.Intent.ACTION_VIEW,
                                            Uri.parse(direction)
                                    );
                                    startActivity(directionIntent);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
