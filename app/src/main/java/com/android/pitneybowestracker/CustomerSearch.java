package com.android.pitneybowestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class CustomerSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search);
        Button button = findViewById(R.id.cSearchBtn);
        final EditText text = findViewById(R.id.customerID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = text.getText().toString();
                Intent intent = new Intent(CustomerSearch.this, OuputActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("str",str);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
