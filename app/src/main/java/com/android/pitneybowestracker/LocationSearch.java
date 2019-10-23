package com.android.pitneybowestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LocationSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        Button button = findViewById(R.id.lSearchBtn);
        final EditText text = findViewById(R.id.location);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = text.getText().toString();
                Intent intent = new Intent(LocationSearch.this, LocationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("str",str);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
