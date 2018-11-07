package com.example.tingc6190.tutorfinder.TutorForm;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.DataObject.Location;
import com.example.tingc6190.tutorfinder.DataObject.Schedule.Schedule;
import com.example.tingc6190.tutorfinder.DataObject.Student;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Search;
import com.example.tingc6190.tutorfinder.Search.Tutor;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class TutorFormBackground extends Fragment {

    private static final int REQUEST_LOCATION_PERMISSIONS = 0x03001;
    String city;
    String state;
    String zipcode;
    String license;
    BackgroundFormListener backgroundFormListener;
    HomeActivity homeActivity;
    Button applyButton;
    EditText city_et;
    EditText state_et;
    EditText zipcode_et;
    EditText license_et;
    Tutor tutor;
    boolean isTutor;
    Student student;
    private LocationManager locationManager;
    public Double latitude;
    public Double longitude;
    private boolean isDevice = true;

    public TutorFormBackground() {
    }

    public interface BackgroundFormListener
    {
        void getBackgroundFormListener(String city, String state, String zipcode, String license);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BackgroundFormListener)
        {
            backgroundFormListener = (BackgroundFormListener) context;
        }
        else
        {
            Log.d("error", "must implement BackgroundFormListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSIONS);

        homeActivity = (HomeActivity) getActivity();
        isTutor = homeActivity.isTutor();

        tutor = homeActivity.getCurrentTutorToEdit();

        return inflater.inflate(R.layout.content_tutor_form_bg_check, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            city_et = getView().findViewById(R.id.tutor_form_city);
            state_et = getView().findViewById(R.id.tutor_form_state);
            zipcode_et = getView().findViewById(R.id.tutor_form_zipcode);
            license_et = getView().findViewById(R.id.tutor_form_license);
            applyButton = getView().findViewById(R.id.background_form_apply_button);

            if (isTutor)
            {
                Location location = tutor.getLocation();

                city_et.setText(location.getCity());
                state_et.setText(location.getState());
                zipcode_et.setText(location.getZipcode());
                license_et.setText(tutor.getLicenseNumber());
            }

            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    city = city_et.getText().toString().trim();
                    state = state_et.getText().toString().trim();
                    zipcode = zipcode_et.getText().toString().trim();
                    license = license_et.getText().toString().trim();

                    //make sure no fields are empty
                    if (TextUtils.isEmpty(city) || TextUtils.isEmpty(state) || TextUtils.isEmpty(zipcode) || TextUtils.isEmpty(license))
                    {
                        Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //make sure zipcode field has 5 digits
                        if (zipcode.length() != 5)
                        {
                            Toast.makeText(getContext(), "Please make sure the zipcode is correct.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //student user becomes a tutor user
                            backgroundFormListener.getBackgroundFormListener(city, state, zipcode, license);
                        }
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

            android.location.Location lastKnown = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
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

                    city_et.setText(addresses.get(0).getLocality());
                    state_et.setText(addresses.get(0).getAdminArea());
                    zipcode_et.setText(addresses.get(0).getPostalCode());

                    //filterZip_et.setText(addresses.get(0).getPostalCode());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d("__ADDRESS__", String.valueOf(latitude));
            Log.d("__ADDRESS__", String.valueOf(longitude));
        }
    }
}
