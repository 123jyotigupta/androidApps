package com.example.developeri.novasample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class mainScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Log.i("in" ,"create in function");
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.i("in", "click event");

                JSONObject jsonobj = new JSONObject();
                try {
                    EditText usernameTxt = (EditText) findViewById(R.id.nameView);
                    EditText passTxt = (EditText) findViewById(R.id.passView);
                    if(!usernameTxt.getText().toString().matches("") && !passTxt.getText().toString().matches("")){
                        jsonobj.put("username", usernameTxt.getText().toString());
                        jsonobj.put("password", passTxt.getText().toString());
                    }else{
                        builder1.setTitle("Error Message");
                        builder1.setMessage("Username and password are required.");
                        builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
                            }
                        });
                        AlertDialog alertdialog = builder1.create();
                        alertdialog.show();
                        return;

                    }
                }catch(JSONException e){}
                new MysyncTask().execute(jsonobj.toString());
            }
        });
        Button signBtn = (Button)findViewById(R.id.signBtn);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("in", "sign up click event");
                Intent signUpScreen = new Intent(mainScreen.this, signUpScreen.class);
                startActivity(signUpScreen);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // Log.i("in" ,"optionsmenuinfunction");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.i("in" ,"item selected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



private class MysyncTask extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... params) {
        // TODO Auto-generated method stub
        postData(params[0]);
        return null;

    }



    public void postData(String data) {
        //make api call here
        DefaultHttpClient httpclient = new DefaultHttpClient();
        //  String url = getString(R.string.url_name);
        HttpPost post = new HttpPost("http://nova.mybluemix.net/api/profiles/login");
        String responseText = null;
        Intent homeScreen = new Intent(mainScreen.this, homeScreen.class);
        startActivity(homeScreen);
        //commented no apis exists
/*
        try {

            StringEntity se = new StringEntity(data);
            se.setContentType("application/json;charset=UTF-8");
            post.setEntity(se);
            HttpResponse httpresponse = httpclient.execute(post);
            responseText = EntityUtils.toString(httpresponse.getEntity());
            Log.i("response",responseText);
            JSONObject response = new JSONObject(responseText);
            //String test =  response.getString("error");
            if(response.has("error")) {
               // JSONArray errorMsg = new JSONArray(response.getString("messages"));
                if (response.getString("error").equals("true")) {
                    Log.i("response11",response.getString("messages") );
                    return;
                }
            }else{
                Log.i("response","ok");
                   Intent homeScreen = new Intent(mainScreen.this, homeScreen.class);
                   startActivity(homeScreen);
            }


        } catch (IOException e) {
            Log.i("response", "http error");
        }
        catch(JSONException e) {
        }
        */
    }

}
}