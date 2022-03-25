package com.example.notificationpart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationRead extends AppCompatActivity {

    // creating variables for our edit text
    private EditText IDEdt;

    // creating variable for button
    private Button getResultsBtn;

    // creating variable for card view and text views.
    private CardView ResultCV;
    private TextView UserIDTV, CompetitionResultTV, CompetitionDescTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_read);

        // initializing all our variables.
        UserIDTV = findViewById(R.id.idTVUserID);
        CompetitionResultTV = findViewById(R.id.idTVCompetitionResult);
        CompetitionDescTV = findViewById(R.id.idTVCompetitionDescription);
        getResultsBtn = findViewById(R.id.idBtnGetResult);
        IDEdt = findViewById(R.id.idEdtID);
        ResultCV = findViewById(R.id.idCVResult);

        // adding click listener for our button.
        getResultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the id text field is empty or not.
                if (TextUtils.isEmpty(IDEdt.getText().toString())) {
                    Toast.makeText(NotificationRead.this, "Please enter id", Toast.LENGTH_LONG).show();
                    return;
                }
                // calling method to load data.
                getCompetitionResult(IDEdt.getText().toString());
            }
        });
    }

    private void getCompetitionResult(String id) {

        // url to post our data
        String url = "http://192.168.1.114/Notification/NotificationRead.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(NotificationRead.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // on below line passing our response to json object.
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are checking if the response is null or not.
                    if (jsonObject.getString("UserID") == null) {
                        // displaying a toast message if we get error
                        Toast.makeText(NotificationRead.this, "Please enter valid id.", Toast.LENGTH_LONG).show();
                    } else {
                        // if we get the data then we are setting it in our text views in below line.
                        UserIDTV.setText(jsonObject.getString("UserID"));
                        CompetitionDescTV.setText(jsonObject.getString("Description"));
                        CompetitionResultTV.setText(jsonObject.getString("Result"));
                        ResultCV.setVisibility(View.VISIBLE);
                    }
                    // on below line we are displaying
                    // a success toast message.
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(NotificationRead.this, "Fail to get course" + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key and value pair to our parameters.
                params.put("id", id);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}
