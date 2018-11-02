package com.example.tingc6190.tutorfinder.Payment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class Payment extends Fragment {

    private static final int REQUEST_CODE = 3;

    final String API_GET_TOKEN = "http://10.0.2.2/braintree/main.php";
    final String API_CHECK_OUT = "http://10.0.2.2/braintree/checkout.php";

    Tutor tutor;
    HomeActivity homeActivity;

    String token,amount;
    HashMap<String,String> paymentHashmap;

    Button requestLessonButton;
    TextView selectDuration_tv;
    TextView totalAmount_tv;
    TextView pricePerHour_tv;
    Integer hourSelected;
    Integer totalPrice;
    PaymentListener paymentListener;

    public Payment() {
    }

    public interface PaymentListener
    {
        void getPaymentInfo(String firstName, String lastName, String price, String pictureUrl, String date, String email);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof PaymentListener)
        {
            paymentListener = (PaymentListener) context;
        }
        else
        {
            Log.d("error", "must implement PaymentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();

        tutor = new Tutor();

        tutor = homeActivity.getTutor();

        return inflater.inflate(R.layout.content_payment_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            selectDuration_tv = getView().findViewById(R.id.select_duration);
            pricePerHour_tv = getView().findViewById(R.id.price_per_hour);
            totalAmount_tv = getView().findViewById(R.id.total_price);

            requestLessonButton = getView().findViewById(R.id.btn_pay);

            new getPaymentToken().execute();

            String priceRate = String.valueOf(tutor.getPrice()) + "/hour";
            pricePerHour_tv.setText(priceRate);

            //select how many hours to request lesson
            selectDuration_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getContext());
                    dialog.setTitle("NumberPicker");
                    dialog.setContentView(R.layout.hour_picker_dialog);

                    Button confirmButton = dialog.findViewById(R.id.num_picker_confirm);
                    Button cancelButton = dialog.findViewById(R.id.num_picker_cancel);

//                    final NumberPicker numberPicker1 = dialog.findViewById(R.id.num_picker1);
//                    numberPicker1.setMaxValue(9);
//                    numberPicker1.setMinValue(0);
//                    numberPicker1.setWrapSelectorWheel(false);
//                    numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//                        @Override
//                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//
//                        }
//                    });
//
//                    final NumberPicker numberPicker2 = dialog.findViewById(R.id.num_picker2);
//                    numberPicker2.setMaxValue(9);
//                    numberPicker2.setMinValue(0);
//                    numberPicker2.setWrapSelectorWheel(false);
//                    numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//                        @Override
//                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//
//                        }
//                    });



                    final NumberPicker numberPicker3 = dialog.findViewById(R.id.num_picker3);
                    numberPicker3.setMaxValue(3);
                    numberPicker3.setMinValue(1);
                    //numberPicker3.setDisplayedValues(values);
                    numberPicker3.setWrapSelectorWheel(false);
                    numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        }
                    });

                    confirmButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            hourSelected = Integer.valueOf(String.valueOf(numberPicker3.getValue()));

                            totalPrice = hourSelected * tutor.getPrice();

                            String total = "Total Price: $" + String.valueOf(totalPrice);

                            totalAmount_tv.setText(total);

                            dialog.dismiss();
                        }
                    });

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            requestLessonButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //request our payment
                    DropInRequest dropInRequest = new DropInRequest().clientToken(token);
                    startActivityForResult(dropInRequest.getIntent(getContext()), REQUEST_CODE);

                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                DropInResult dropInResult = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce paymentNonce = dropInResult.getPaymentMethodNonce();
                assert paymentNonce != null;
                String nonce = paymentNonce.getNonce();

                amount = String.valueOf(totalPrice);

                paymentHashmap = new HashMap<>();
                paymentHashmap.put("amount", amount);
                paymentHashmap.put("nonce", nonce);

                sendPayments();

            }
            else
            {
                //Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("error", "__");
            }
        }
    }


    private void sendPayments()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_CHECK_OUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("Successful"))
                        {
                            Log.d("__NAME__", tutor.getFirstName() + " " + tutor.getLastName());
                            Log.d("__PRICE__", String.valueOf(totalPrice));
                            Log.d("__IMAGE__", tutor.getPicture());

                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                            String currentDate = sdf.format(new Date());

                            if (!TextUtils.isEmpty(tutor.getPicture().trim()))
                            {
                                paymentListener.getPaymentInfo(tutor.getFirstName(), tutor.getLastName(), String.valueOf(totalPrice), tutor.getPicture(), currentDate, tutor.getEmail());

                            }
                            else
                            {
                                paymentListener.getPaymentInfo(tutor.getFirstName(), tutor.getLastName(), String.valueOf(totalPrice), " ", currentDate, tutor.getEmail());

                            }
                            Toast.makeText(getContext(), "Payment Successful", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        })

        {

            @Override
            protected Map<String, String> getParams() {

                if(paymentHashmap == null)
                {
                    return null;
                }

                Map<String, String> params = new HashMap<>();
                for (String key: paymentHashmap.keySet())
                {
                    params.put(key, paymentHashmap.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(stringRequest);
    }


    //try to get our token
    @SuppressLint("StaticFieldLeak")
    private class getPaymentToken extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            token = responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    Log.d("error", exception.toString());
                }
            });
            return null;
        }
    }
}
