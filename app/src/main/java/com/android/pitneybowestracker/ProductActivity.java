package com.android.pitneybowestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_activity);
        final TextView prod1 = findViewById(R.id.prod1);
        final TextView prod2 = findViewById(R.id.prod2);
        final TextView prod3 = findViewById(R.id.prod3);
        final TextView prod4 = findViewById(R.id.prod4);
        final String BASE_URL = "https://fb5b5100.ngrok.io/api/Shipping?filter=%7B\"where\"%3A%7B\"shipment\"%3A\"resource%3Aorg.pitney.hack.Shipment%23";
        final String END_URL = "\"%7D%2C\"order%20by\"%3A%7B\"timestamp\"%3A\"asc\"%7D%7D";
        Bundle bundle = getIntent().getExtras();
        final String str = bundle.getString("str");
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                String url1 = BASE_URL+str+END_URL;
                Request request = new Request.Builder()
                        .url(url1)
                        .build();

                Response response = null;

                try {
                    response = client.newCall(request).execute();
                    return response.body().string();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                String myResponse = o.toString();
                prod1.setText(str);
                try {

                    JSONArray jsonArray = new JSONArray(myResponse);
                    JSONObject root = jsonArray.getJSONObject(0);
                    String dimension = root.optString("dimension");
                    String weight = root.optString("weight");
//                    Toast.makeText(ProductActivity.this,myResponse,Toast.LENGTH_SHORT).show();
                    String transaction = "Packing ";
                    prod2.setText(dimension);
                    prod3.setText(weight);
                    JSONObject jsonObject;
                    for(int i=0; i<jsonArray.length();i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String shipper = jsonObject.optString("shipper");
                        String last3 = shipper.substring(shipper.length()-3);
                        transaction += " --->  " + last3;
                    }
                    prod4.setText(transaction);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }
}
