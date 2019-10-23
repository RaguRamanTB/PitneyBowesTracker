package com.android.pitneybowestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Search_By extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_layout);
    }

    public void switchToCustomerSearch (View v) {
        Intent intent = new Intent(this, CustomerSearch.class);
        startActivity(intent);
    }

    public void switchToProductSearch (View v) {
        Intent intent1 = new Intent(this, ProductSearch.class);
        startActivity(intent1);
    }

    public void switchToLocationSearch (View v) {
        Intent intent2 = new Intent(this, LocationSearch.class);
        startActivity(intent2);
    }
}
