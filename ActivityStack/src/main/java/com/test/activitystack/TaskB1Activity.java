package com.test.activitystack;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Scott on 2020/9/22 0022
 */
public class TaskB1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        Log.e("xiongkai", "TaskB1Activity onCreate  task-id = " + info.id);
        setContentView(R.layout.activity_taskb1);
        Button taska2 = findViewById(R.id.taska_2);
        taska2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(TaskA2Activity.class);
            }
        });
    }

    void start(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        Log.e("xiongkai", "TaskB1Activity onDestroy");
        super.onDestroy();
    }
}
