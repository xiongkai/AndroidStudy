package com.test.bindertestb;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.binder.test.AToB;
import com.binder.test.BToA;

import androidx.annotation.Nullable;

/**
 * Created by Scott on 2020/12/3 0003
 */
public class ServiceB extends Service {
    public AToBImpl impl = new AToBImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("xxx", "xxxxxxxxxx");
        return impl;
    }

    public static class AToBImpl extends AToB.Stub {

        @Override
        public boolean hello(IBinder binder) throws RemoteException {
            Log.e("testb", "thread3 = " + Thread.currentThread().getName());
            BToA bToA = BToA.Stub.asInterface(binder);
            bToA.hello("BToA");
            Log.e("testb", "thread4 = " + Thread.currentThread().getName());
            return false;
        }
    }
}
