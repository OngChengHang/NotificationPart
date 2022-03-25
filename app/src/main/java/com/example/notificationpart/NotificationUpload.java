package com.example.notificationpart;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationUpload extends AppCompatActivity {

    // creating variables for our edit text
    private EditText IDEdt, UserIDEdt, competitionResultEdt, competitionDescriptionEdt;

    // creating variable for button
    private Button BtnSubmit;

    // creating a strings for storing our values from edittext fields.
    private String id, UserID, competitionResult, competitionDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationupload);

        // initializing our edittext and buttons
        IDEdt = findViewById(R.id.IDEdt);
        UserIDEdt = findViewById(R.id.UserIDEdt);
        competitionResultEdt = findViewById(R.id.competitionResultEdt);
        competitionDescriptionEdt= findViewById(R.id.competitionDescriptionEdt);
        BtnSubmit = findViewById(R.id.BtnSubmit);
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                id = IDEdt.getText().toString();
                UserID = UserIDEdt.getText().toString();
                competitionResult = competitionResultEdt.getText().toString();
                competitionDescription = competitionDescriptionEdt.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(id)) {
                    IDEdt.setError("Please enter ID");
                } else if (TextUtils.isEmpty(UserID)) {
                    UserIDEdt.setError("Please enter User ID");
                } else if (TextUtils.isEmpty(competitionResult)) {
                    competitionResultEdt.setError("Please enter Result");
                } else if (TextUtils.isEmpty(competitionDescription)) {
                    competitionDescriptionEdt.setError("Please enter Description");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToDatabase(id,UserID, competitionResult, competitionDescription);
                }
            }
        });
    }

    private void addDataToDatabase(String id,String UserID, String competitionResult, String competitionDescription) {

        // url to post our data
        String url = "http://192.168.1.114/Notification/NotificationUpload.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(NotificationUpload.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(NotificationUpload.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // and setting data to edit text as empty
                IDEdt.setText("");
                UserIDEdt.setText("");
                competitionResultEdt.setText("");
                competitionDescriptionEdt.setText("");
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(NotificationUpload.this, "Fail to get response = " + error, Toast.LENGTH_LONG).show();
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

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("id", id);
                params.put("UserID", UserID);
                params.put("competitionResult", competitionResult);
                params.put("competitionDescription", competitionDescription);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}
