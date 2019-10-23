package com.android.pitneybowestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class OrderProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] products = {"P1","P2","P3","P4","P5"};
    String[] sellers = {"S1","S2","S3"};
    String[] locations = {"COIMBATORE","SALEM","TRICHY","CHENNAI","BANGALORE"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);

        Spinner spin = (Spinner) findViewById(R.id.prodID);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,products);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);


        Spinner spin2 = (Spinner) findViewById(R.id.sellerID);
        ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sellers);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(bb);
        spin2.setOnItemSelectedListener(this);

        Spinner spin3 = (Spinner) findViewById(R.id.locationID);
        ArrayAdapter cc = new ArrayAdapter(this,android.R.layout.simple_spinner_item,locations);
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(cc);
        spin3.setOnItemSelectedListener(this);



    }

    public void putJSON () {
        EditText textView0 = (EditText) findViewById(R.id.ordersID);
        EditText textView1 = (EditText) findViewById(R.id.customersID);
        Spinner spin = (Spinner) findViewById(R.id.prodID);
        Spinner spin2 = (Spinner) findViewById(R.id.sellerID);
        Spinner spin3 = (Spinner) findViewById(R.id.locationID);

        final String text0 = textView0.getText().toString();
        final String text1 = textView1.getText().toString();
        final String text2 = spin.getSelectedItem().toString();
        final String text3 = spin2.getSelectedItem().toString();
        final String text4 = spin3.getSelectedItem().toString();
        final JSONObject upload = new JSONObject();
        try {
            upload.put("$class","org.pitney.hack.Order");
            upload.put("order_id",text0);
            upload.put("customer",text1);
            upload.put("product",text2);
            upload.put("seller",text3);
            upload.put("location",text4);
            final String JSON = upload.toString();
            sendDataToServer(JSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void sendDataToServer(String json) {
        final String JSON = json;
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                return getServerResponse(JSON);
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(OrderProduct.this, "Order Successfull", Toast.LENGTH_LONG);
            }
        }.execute();
    }

    private String getServerResponse(String json) {
        final String BASE_URL = "https://fb5b5100.ngrok.io/api/Order";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                .build();

        Call call = okHttpClient.newCall (request);
        Response response = null;

        try {
            response = call.execute();
            Toast.makeText(OrderProduct.this, "Order Successful", Toast.LENGTH_LONG).show();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        HttpPost post = new HttpPost(BASE_URL);
//        try {
//            StringEntity entity = new StringEntity(json);
//            post.setEntity(entity);
//            post.setHeader("Content-type", "application/json");
//
//            DefaultHttpClient client = new DefaultHttpClient();
//            BasicResponseHandler handler = new BasicResponseHandler();
//            String response = client.execute(post,handler);
//            return response;
//
//        } catch (UnsupportedEncodingException e) {
//            Log.d("JWP",e.toString());
//        } catch (ClientProtocolException e) {
//            Log.d("JWP",e.toString());
//        } catch (IOException e) {
//            Log.d("JWP",e.toString());
//        }
        return "Unable to contact server!";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        Spinner spin3 = (Spinner)parent;

        if(spin.getId() == R.id.prodID)
        {
            Toast.makeText(this, "Your chose :" + products[position],Toast.LENGTH_SHORT).show();
        }
        if(spin2.getId() == R.id.sellerID)
        {
            Toast.makeText(this, "Your chose :" + sellers[position],Toast.LENGTH_SHORT).show();
        }
        if(spin3.getId() == R.id.locationID)
        {
            Toast.makeText(this, "Your chose :" + locations[position],Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Toast.makeText(this, "Choose product and seller !", Toast.LENGTH_SHORT).show();
    }

    public void postValue(View view) {
        putJSON();
        Toast.makeText(OrderProduct.this,"Order Placed",Toast.LENGTH_LONG).show();
    }
}
