package com.example.tingc6190.tutorfinder.Welcome;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.MainActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Search;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class Welcome extends Fragment {

    private static final int REQUEST_LOCATION_PERMISSIONS = 0x03001;
    private WelcomeListener welcomeListener;
    private String subject;
    private String zipcode;
    private LocationManager locationManager;
    public Double latitude;
    public Double longitude;
    EditText filterZip_et;
    HomeActivity homeActivity;
    private boolean isDevice = false;


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

//        getPermission();

        //lastKnown = new Location();

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSIONS);

        homeActivity = (HomeActivity) getActivity();

//        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
//                REQUEST_LOCATION_PERMISSIONS);




        return inflater.inflate(R.layout.content_welcome_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            Button searchButton = getView().findViewById(R.id.search_welcome);
            Spinner subjectSpinner = getView().findViewById(R.id.setting_subject_spinner);

            filterZip_et = getView().findViewById(R.id.filter_zip);


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

                    zipcode = filterZip_et.getText().toString().trim();

                    //make sure no fields are empty
                    if (TextUtils.isEmpty(zipcode))
                    {
                        Toast.makeText(getContext(), "Please enter a zipcode.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.d("__SEARCH_TAPPED__", subject);

                        //homeActivity.getLocationOfUser();

                        welcomeListener.getSearchSettings(subject, zipcode);
                        //getLocation();

                        getFragmentManager().beginTransaction()
                                .replace(R.id.content_container, new Search())
                                .commit();
                    }
                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            //lastKnown = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(android.location.Location location) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
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

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    2000,
                    10.0f,
                    locationListener);

            assert locationManager != null;

            Location lastKnown = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastKnown != null)
            {
                latitude = lastKnown.getLatitude();
                longitude = lastKnown.getLongitude();
            }

            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());


            if (isDevice)
            {
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude,1);
                    //List<Address> addresses = geocoder.getFromLocation(40.514658, -74.393104, 1);

                    Log.d("__ADDRESS__", addresses.get(0).getPostalCode());
                    Log.d("__ADDRESS__", addresses.get(0).getLocality());
                    Log.d("__ADDRESS__", addresses.get(0).getAdminArea());

                    filterZip_et.setText(addresses.get(0).getPostalCode());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d("__ADDRESS__", String.valueOf(latitude));
            Log.d("__ADDRESS__", String.valueOf(longitude));
        }
    }

//    @Override
//    public void onLocationChanged(Location location) {
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }

    public interface WelcomeListener
    {
        void getSearchSettings(String subject, String zipcode);
    }

    private void getPermission()
    {

        if (ContextCompat.checkSelfPermission(homeActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(homeActivity, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
            else
            {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(homeActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSIONS);
            }
        }
        else
        {
            // Permission has already been granted
            //lastKnown = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        }

    }

    private void getLocation()
    {




//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                    2000,
//                    10.0f,
//                    (LocationListener) getContext());

        //locationManager.requestLocationUpdates();

//        if (Geocoder.isPresent())
//        {
//
//        }
//        else
//        {
//            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//
//            try {
//                List<Address> addresses = geocoder.getFromLocation(latitude, longitude,1);
//
//                Log.d("__ADDRESS__", addresses.get(0).getPostalCode());
//                Log.d("__ADDRESS__", addresses.get(0).getLocality());
//                Log.d("__ADDRESS__", addresses.get(0).getAdminArea());
//
//                filterZip_et.setText(addresses.get(0).getPostalCode());
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude,1);

            Log.d("__ADDRESS__", addresses.get(0).getPostalCode());
            Log.d("__ADDRESS__", addresses.get(0).getLocality());
            Log.d("__ADDRESS__", addresses.get(0).getAdminArea());

            filterZip_et.setText(addresses.get(0).getPostalCode());

        } catch (IOException e) {
            e.printStackTrace();
        }



        Log.d("__ADDRESS__", String.valueOf(latitude));
        Log.d("__ADDRESS__", String.valueOf(longitude));
    }
}
