package com.example.sylviatan.onlinedatabase;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private EditText editTextID, editTextName, editTextAge, editTextMarried;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextID = findViewById(R.id.editTextID);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextMarried = findViewById(R.id.editTextMarried);

    }

    //function to save record
    public void save(View v){

        String id = editTextID.getText().toString();
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        String married = editTextMarried.getText().toString();

        User user = new User(id, name, age, married);

        try {
            makeServiceCall(this, "https://lipeng.000webhostapp.com/info.php", user);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_LONG).show();

        }



    }

    public void makeServiceCall(Context context, String url, final User user){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Send data
        try{
        StringRequest postRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");

                            if(success == 0){
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("id", user.getId());
                params.put("name", user.getName());
                params.put("age", user.getAge());
                params.put("married", user.getMarried());
                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> param = new HashMap<>();
                param.put("Content-Type","application/x-www-form-urlencoded");
                return param;

            }

        };
        requestQueue.add(postRequest);
    }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void reset(){
        editTextID.setText("");
        editTextName.setText("");
        editTextAge.setText("");
        editTextMarried.setText("");

    }
}
