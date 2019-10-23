package com.android.pitneybowestracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static ProgressBar loading;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private static String URL_LOGIN = "http://192.168.234.1/login.php";

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        loading = (ProgressBar) view.findViewById(R.id.loading);
        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);
            show_hide_password.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    private void setListeners() {
        loginButton.setOnClickListener(this);

        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());
                        } else {
                            show_hide_password.setText(R.string.show_pwd);

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;
        }

    }


//    private void onLogin(final String musername, final String mpassword) {
//        loading.setVisibility(View.VISIBLE);
//        loginButton.setVisibility(View.GONE);
////        String type = "Login";
////        backgroundWorker background = new backgroundWorker(getContext().getApplicationContext());
////        background.execute(type, musername, mpassword;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("login");
//
//                    if (success.equals("1")) {
//
//                        Toast.makeText(getActivity(), "Login Success !", Toast.LENGTH_SHORT).show();
//
//                        loading.setVisibility(View.GONE);
//
////                        Intent intent = new Intent(getActivity(), Search_By.class);
////                        startActivity(intent);
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    loading.setVisibility(View.GONE);
//                    loginButton.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), "Error " + e.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loading.setVisibility(View.GONE);
//                loginButton.setVisibility(View.VISIBLE);
//                Toast.makeText(getActivity(), "Error " + error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("musername", musername);
//                params.put("mpassword", mpassword);
//                return params;
//
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                6000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//        );
//
//        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
//        requestQueue.add(stringRequest);
//
//        Intent intent = new Intent(getActivity(), Search_By.class);
//        startActivity(intent);
//    }


    private void checkValidation() {
        Intent intent = new Intent(getActivity(), Search_By.class);
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        } else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
        else {
//            onLogin(getEmailId, getPassword);
            startActivity(intent);
        }

    }


}
