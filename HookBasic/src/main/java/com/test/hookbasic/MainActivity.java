package com.test.hookbasic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.hookbasic.hooks.HookActivityManagerService;
import com.test.hookbasic.hooks.HookBinderQueryLocalInterface;
import com.test.hookbasic.hooks.HookNotificationManager;
import com.test.hookbasic.hooks.HookOnClickListener;
import com.test.hookbasic.hooks.HookPackageManagerService;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button hookClick = findViewById(R.id.hookClick);
        HookOnClickListener.hook(hookClick);
        //
        HookNotificationManager.hook(this);
        Button hookNotification = findViewById(R.id.hookNotification);
        hookNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HookNotificationManager.test(MainActivity.this);
            }
        });
        //
        HookBinderQueryLocalInterface.hook();
        Button hookClipBoard = findViewById(R.id.hookClipBoard);
        hookClipBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HookBinderQueryLocalInterface.test(MainActivity.this);
            }
        });
        //
        HookActivityManagerService.hook();
        Button hookAMS = findViewById(R.id.hookAMS);
        hookAMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HookActivityManagerService.test(MainActivity.this);
            }
        });
        //
        HookPackageManagerService.hook(this);
        Button hookPMS = findViewById(R.id.hookPMS);
        hookPMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HookPackageManagerService.test(MainActivity.this);
            }
        });
    }
}
