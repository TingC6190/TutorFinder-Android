package com.example.tingc6190.tutorfinder.Welcome;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.tingc6190.tutorfinder.MainActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Search;

import static android.content.Context.LOCATION_SERVICE;

public class Welcome extends Fragment implements LocationListener {

    private static final int REQUEST_LOCATION_PERMISSIONS = 0x03001;
    private WelcomeListener welcomeListener;
    private String subject;
    private String zipcode;
    private LocationManager locationManager;
    public Double latitude;
    public Double longitude;


    public Welcome() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof WelcomeListener)
        {
            welcomeListener = (WelcomeListener) context;
        }
        else
        {
            Log.d("error", "must implement WelcomeListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION_PERMISSIONS);

        return inflater.inflate(R.layout.content_welcome_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            Button searchButton = getView().findViewById(R.id.search_welcome);
            Spinner subjectSpinner = getView().findViewById(R.id.setting_subject_spinner);



            ArrayAdapter<CharSequence> subjectAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.spinner_subject_any, android.R.layout.simple_spinner_item);
            subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            subjectSpinner.setAdapter(subjectAdapter);

            //get our selected subject in the spinner
            subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    subject = parent.getItemAtPosition(position).toString();
                    //Log.d("__SPINNER__", subject);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("__SEARCH_TAPPED__", subject);

                    //welcomeListener.getSearchSettings(subject, "");
                    getLocation();

//                    getFragmentManager().beginTransaction()
//                            .replace(R.id.content_container, new Search())
//                            .commit();
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //if permission not request, ask again
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
        }

        //show our map fragment
        else
        {

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
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

    public interface WelcomeListener
    {
        void getSearchSettings(String subject, String zipcode);
    }

    private void getLocation()
    {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            // Request location updates using 'this' as our LocationListener.
            //locationManager = new LocationManager();

            locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

            //locationManager.requestLocationUpdates(Loca);

            assert locationManager != null;
            Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnown != null)
            {
                latitude = lastKnown.getLatitude();
                longitude = lastKnown.getLongitude();
            }

            Log.d("__LATITUDE___", String.valueOf(latitude));
            Log.d("__LONGITUDE__", String.valueOf(longitude));
        }
    }
}
