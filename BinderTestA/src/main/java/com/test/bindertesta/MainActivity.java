package com.test.bindertesta;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.binder.test.AToB;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    AToB aToB;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("xiongkai", "onServiceConnected");
            aToB = AToB.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView fab = findViewById(R.id.textView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e("testa", "thread1 = " + Thread.currentThread().getName());
                    aToB.hello(new BToA());
                    Log.e("testa", "thread2 = " + Thread.currentThread().getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        Intent remoteService = new Intent("testb");
        try{
            PackageManager pm = getPackageManager();
            List<ResolveInfo> resolveInfos = pm.queryIntentServices(remoteService, 0);
            if (resolveInfos == null || resolveInfos.size()!= 1) {
                return;
            }
            ResolveInfo info = resolveInfos.get(0);
            String packageName = info.serviceInfo.packageName;
            String className = info.serviceInfo.name;
            ComponentName component = new ComponentName(packageName,className);
            Intent explicitIntent = new Intent();
            explicitIntent.setComponent(component);
            bindService(explicitIntent, connection, Context.BIND_AUTO_CREATE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class BToA extends com.binder.test.BToA.Stub {

        @Override
        public boolean hello(String msg) throws RemoteException {
            Log.e("testa", "thread3 = " + Thread.currentThread().getName());
            Log.e("testa", "BToA = " + msg);
            Log.e("testa", "thread4 = " + Thread.currentThread().getName());
            return false;
        }
    }
}
