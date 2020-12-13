package com.test.activitystack;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Scott on 2020/9/22 0022
 */
public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        Log.e("xiongkai", "MyActivity onCreate, task id = " + info.id);
        setContentView(R.layout.activity_my);
        Button taska1 = findViewById(R.id.taska_1);
        taska1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(TaskA1Activity.class);
            }
        });
        Button test1 = findViewById(R.id.test_1);
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("xiongkai.test");
                startActivity(intent);
            }
        });
    }

    void start(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        Log.e("xiongkai", "MyActivity onDestroy");
        super.onDestroy();
    }
}
