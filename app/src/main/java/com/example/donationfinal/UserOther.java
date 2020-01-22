package com.example.donationfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UserOther extends AppCompatActivity {

    private static final String KEY_TYPE = "type";
    private static final String KEY_NAME = "name";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PICKUPFROM = "pickupFrom";
    private static final String KEY_PICKUPDAY = "pickupDay";
    private static final String KEY_PREFERREDTIME = "preferredTime";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_STATUS="status";
    private static final String KEY_MESSAGE="message";
    private static final String KEY_CATEGORY="category";

    //private static final String KEY_created_date = "created_date";
    private static final String KEY_EMPTY = "";
    private static Object setOnClickListener;
    private EditText etname;
    private EditText ettype;
    private EditText etpickupFrom, ettitle;
    private EditText etpreferredTime, etquantity, etpickupDay;
    private String name, type, pickupFrom, preferredTime, title;
    private String pickupDay;
    private String quantity,category;
    private ProgressDialog pDialog;
    private String user_other_url = "https://daan2020.000webhostapp.com/jsondetail.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_clothes);

        etname = findViewById(R.id.name);
        ettype = findViewById(R.id.type);
        etpickupDay = findViewById(R.id.pickupDay);
        etpickupFrom = findViewById(R.id.pickupFrom);
        etpreferredTime = findViewById(R.id.preferredTime);
        etquantity = findViewById(R.id.quantity);
        ettitle = findViewById(R.id.title);


        Button next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                name = etname.getText().toString().toLowerCase().trim();
                quantity = etquantity.getText().toString().trim();
                preferredTime = etpreferredTime.getText().toString().trim();
                type = ettype.getText().toString().trim();
                pickupDay = etpickupDay.getText().toString().trim();
                pickupFrom = etpickupFrom.getText().toString().trim();

                title = ettitle.getText().toString().trim();
                if (validateInputs()) {
                    userOther();
                }

            }
        });

    }

    private static void setOnClickListener(View.OnClickListener onClickListener) {
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(UserOther.this);
        pDialog.setMessage("Processing");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
        finish();
    }
        private void userOther() {
            displayLoader();
            JSONObject request = new JSONObject();
            try {
                //Populate the request parameters
                request.put(KEY_NAME, name);
                request.put(KEY_TYPE, type);
                request.put(KEY_PICKUPDAY, pickupDay);
                request.put(KEY_PICKUPFROM, pickupFrom);
                request.put(KEY_PREFERREDTIME,preferredTime);
                request.put(KEY_QUANTITY, quantity);
                request.put(KEY_TITLE,title);
                request.put(KEY_CATEGORY,"Food");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                    (Request.Method.POST, user_other_url, request, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            pDialog.dismiss();
                            try {
                                //Check if user got registered successfully
                                if (response.getInt(KEY_STATUS) == 0) {
                                    //Set the user session
                                    session.loginUser(title,name);
                                    Toast.makeText(UserOther.this, response.getString(KEY_MESSAGE),Toast.LENGTH_LONG).show();
                                    loadDashboard();



                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.dismiss();

                            //Display error message whenever an error occurs
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

    }

    private boolean validateInputs() {

        if (KEY_EMPTY.equals(title)) {
            ettitle.setError("title cannot be empty");
            ettitle.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(type)) {
            ettype.setError("type cannot be empty");
            ettype.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(name)) {
            etname.setError(" Item name cannot be empty");
            etname.requestFocus();
            return false;

        }

        if (KEY_EMPTY.equals(pickupDay)) {
            etpickupDay.setError("Pickupday cannot be empty");
            etpickupDay.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(pickupFrom)) {
            etpickupFrom.setError("PickupFrom cannot be empty");
            etpickupFrom.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(preferredTime)) {
            etpreferredTime.setError("Preferred cannot be empty");
            etpreferredTime.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(quantity)) {
            etquantity.setError("Quantity cannot be empty");
            etquantity.requestFocus();
            return false;
        }


        return true;
    }

}
