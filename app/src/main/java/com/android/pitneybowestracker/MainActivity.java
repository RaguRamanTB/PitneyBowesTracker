package com.android.pitneybowestracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

    }

    public void switchToCustomer (View view) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer1, new UserLogin(),
                        Utils.UserLogin).commit();
    }

    public void switchToManager (View view) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer1, new Login_Fragment(),
                        Utils.Login_Fragment).commit();
    }

//    public void replaceMainActivity() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }

//    @Override
//    public void onBackPressed() {
//        Fragment userLogin = fragmentManager
//                .findFragmentByTag(Utils.UserLogin);
//        Fragment loginFrag = fragmentManager
//                .findFragmentByTag(Utils.Login_Fragment);
//
//        if (loginFrag != null)
//            replaceMainActivity();
//        else if (userLogin != null)
//            replaceMainActivity();
//        else
//            super.onBackPressed();
//    }

}
