package com.test.opensource;

import android.os.Bundle;

import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LeakCanary.install(getApplication());
        EventBus.getDefault().register(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
