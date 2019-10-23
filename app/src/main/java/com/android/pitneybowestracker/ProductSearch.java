package com.android.pitneybowestracker;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

import android.Manifest;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.SparseArray;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;


public class ProductSearch extends AppCompatActivity {
    Button scanBtn;
    Button searchBtn;
    EditText mTvResult;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EditText text = (EditText) findViewById(R.id.tv_result);
        setContentView(R.layout.activity_product_search);
        scanBtn = (Button) findViewById(R.id.scanBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        mTvResult = (EditText) findViewById(R.id.tv_result);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
//        scanBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProductSearch.this, ScanActivity.class);
//                startActivityForResult(intent, REQUEST_CODE);
//            }
//        });
//
//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str = text.getText().toString();
//                Intent intent = new Intent(ProductSearch.this, ProductActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("str",str);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
    }


    public void scanBAR(View v) {
        Intent intent = new Intent(ProductSearch.this, ScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void printProduct (View v) {
        final EditText text = (EditText) findViewById(R.id.tv_result);
        String str = text.getText().toString();
        Intent intent = new Intent(ProductSearch.this, ProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("str",str);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                if (data != null) {
                    final Barcode barcode = data.getParcelableExtra("barcode");
                    mTvResult.post(new Runnable() {
                        @Override
                        public void run() {
                            mTvResult.setText(barcode.displayValue);
                        }
                    });
                } else {
                    mTvResult.setText("No BarCode/QRCode found");
                }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
