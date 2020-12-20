package com.test.hookbasic;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Scott on 2020/12/14 0014
 */
public class StubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("xiongkai", "onCreate " + getLocalClassName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("xiongkai", "onStart " + getLocalClassName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("xiongkai", "onResume " + getLocalClassName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("xiongkai", "onPause " + getLocalClassName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("xiongkai", "onStop " + getLocalClassName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("xiongkai", "onDestroy " + getLocalClassName());
    }
}
