package com.example.sylviatan.onlinedatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MESSAGE";
    private static String GET_URL = "https://lipeng.000webhostapp.com/select.php";
    ListView listViewUser;
    private ProgressDialog progressDialog;
    List<User> userlist;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize view objects
        listViewUser = findViewById(R.id.listViewRecords);
        progressDialog = new ProgressDialog(this);
        userlist = new ArrayList<>();

        //checking network connection
        if(!isConnected()){
            Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            loadAllUser(getApplicationContext(), GET_URL);
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadAllUser(Context context,  String url){
        requestQueue = Volley.newRequestQueue(context);

        if(!progressDialog.isShowing())
            progressDialog.setMessage("Sync with server...");
            progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                userlist.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject userResponse = (JSONObject) response.get(i);

                        String id = userResponse.getString("id");
                        String name = userResponse.getString("name");
                        String age = userResponse.getString("age");
                        String married = userResponse.getString("married");

                        User user = new User(id, name, age, married);
                        userlist.add(user);
                        loadUser();
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

        jsonArrayRequest.setTag(TAG);
        requestQueue.add(jsonArrayRequest);
    }

    private void loadUser(){

        final UserAdapter userAdapter = new UserAdapter(this, R.layout.content_main, userlist);
        listViewUser.setAdapter(userAdapter);
        Toast.makeText(getApplicationContext(), "Count: " + userlist.size(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(requestQueue!=null){
            requestQueue.cancelAll(TAG);
        }
    }


}
