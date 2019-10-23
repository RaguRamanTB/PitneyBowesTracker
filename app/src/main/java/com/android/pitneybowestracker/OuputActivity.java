package com.android.pitneybowestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class OuputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouput);
        final TextView out1 = findViewById(R.id.out1);
        final TextView textView = (TextView) findViewById(R.id.outputFinal);
        final String BASE_URL = "https://fb5b5100.ngrok.io/api/Order?filter=%7B\"where\"%3A%7B\"customer\"%3A\"resource%3Aorg.pitney.hack.Customer%23";
        final String END_URL = "\"%7D%7D";
        Bundle bundle = getIntent().getExtras();
        final String str = bundle.getString("str");
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                String urlFinal = BASE_URL+str+END_URL;
                Request request = new Request.Builder()
                        .url(urlFinal)
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
                out1.setText(str);
                try {
                    JSONArray jsonArray = new JSONArray(myResponse);
                    JSONObject jsonObject;
                    String data = "";
                    for(int i=0; i<jsonArray.length();i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String order_id = jsonObject.optString("order_id");
                        String product = jsonObject.optString("product");
                        String L2pro = product.substring(product.length()-2);
                        String seller = jsonObject.optString("seller");
                        String L2sell = seller.substring(seller.length()-2);
                        String location = jsonObject.optString("location");
                        String L2loc = location.substring(35 ,location.length());

                        data += "\nOrder ID  :  "+order_id+"\nProduct ID  :  "+L2pro+"\nSeller ID  :  "+L2sell+"\nLocation  :  "+L2loc+"\n\n";
                    }
                    textView.setText(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }
}
