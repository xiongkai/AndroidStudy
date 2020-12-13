package com.test.activitystack;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Scott on 2020/9/22 0022
 */
public class TaskA2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        Log.e("xiongkai", "TaskA2Activity onCreate task-id = " + info.id);
        setContentView(R.layout.activity_taska2);
    }

    @Override
    protected void onDestroy() {
        Log.e("xiongkai", "TaskA2Activity onDestroy");
        super.onDestroy();
    }
}
