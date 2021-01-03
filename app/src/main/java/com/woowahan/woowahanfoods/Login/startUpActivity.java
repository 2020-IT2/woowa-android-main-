package com.woowahan.woowahanfoods.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.woowahan.woowahanfoods.DataModel.User;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class startUpActivity extends AppCompatActivity {
    private static final String TAG = "KHK";
    public User user;
    Intent intent;
    public int scenarioNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 상태바 색깔
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
            }
        }else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.WHITE);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start);

        SharedPreferences sharedPref = getSharedPreferences(
                "auto", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String autoLogin = sharedPref.getString("autoLogin", null);
        String keyHash = com.kakao.util.helper.Utility.getKeyHash(this);
        Log.d("HASH_KEY", keyHash);
        if (autoLogin != null) {
            String expStr = sharedPref.getString("expDate", null);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date expDate;
            Date curDate = new Date();
            try {
                expDate = format.parse(expStr);
                if (curDate.before(expDate)) {
                    // extend expired date
                    Calendar c = Calendar.getInstance();
                    c.setTime(curDate);
                    c.add(Calendar.DATE, 25);
                    String expDateStr = format.format(c.getTime());
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.putString("expDate", expDateStr);
//                    edit.clear();
                    edit.commit();
                    // load user object and pass it to main Activity
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    this.finish();
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.replaceFragment(new Login());
        return;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main, fragment);
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStackImmediate();
        transaction.commit();
    }

    public void login(long id, String token) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
//        RetrofitAdapter adapter = new RetrofitAdapter();
//        RetrofitService service = adapter.getInstance(this);
//        Call<LoginResponse> call = service.kakaoLogin(id, token);
//        call.enqueue(new retrofit2.Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
//                if (response.isSuccessful()) {
//                    LoginResponse result = response.body();
//                    if (result.checkError(getApplicationContext()) != 0) {
//                        if (result.errorCode == 3) {
//                            // if need to sign up
//                            Log.d(TAG, "onResponse: Fails " + response.body().status);
//                        }
//                        return;
//                    }
//                    if (result.status.equals("<success>")) {
//                        User userInfo = result.body;
//                        Log.d("KHK", user.toString());
//                        // make curDate + 30 string as expired date
//                        Calendar c = Calendar.getInstance();
//                        c.setTime(new Date());
//                        c.add(Calendar.DATE, 25);
//                        String expDateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(c.getTime());
//                        // make user object to string to store
//                        Gson gson = new Gson();
//                        String json = gson.toJson(userInfo);
//                        // store to sharePreference for future Use
//                        SharedPreferences sharedPref = getSharedPreferences(
//                                "auto", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putString("autoLogin", "true");
//                        editor.putString("userObject", json);
//                        editor.putString("expDate", expDateStr);
//                        editor.commit();
//                        // make intent and start main activity
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        return;
//                    }
//
//                } else {
//                    Log.d(TAG, "onResponse: Fails " + response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.getMessage());
//            }
//        });
    }


}


