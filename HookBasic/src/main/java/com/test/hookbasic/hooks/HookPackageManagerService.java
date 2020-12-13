package com.test.hookbasic.hooks;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by Scott on 2020/12/13 0013
 */
public class HookPackageManagerService {

    public static void hook(Context context){
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");

            // 获取 gDefault 这个字段, 想办法替换它
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object currentActivityThread = currentActivityThreadField.get(null);

            Field mPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
            mPackageManagerField.setAccessible(true);

            Object rawPackageManager = mPackageManagerField.get(currentActivityThread);

            // 创建一个这个对象的代理对象, 然后替换这个字段, 让我们的代理对象帮忙干活
            Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iPackageManagerInterface}, new IPackageManagerHandler(rawPackageManager));
            mPackageManagerField.set(currentActivityThread, proxy);

            // 2. 替换 ApplicationPackageManager里面的 mPM对象
            /*PackageManager pm = context.getPackageManager();
            Field mPmField = pm.getClass().getDeclaredField("mPM");
            mPmField.setAccessible(true);
            mPmField.set(pm, proxy);*/
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    static class IPackageManagerHandler implements InvocationHandler {

        private Object mBase;

        public IPackageManagerHandler(Object base) {
            mBase = base;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e("xiongkai", "hey, baby; you are hooked!!");
            Log.e("xiongkai", "method:" + method.getName() + " called with args:" + Arrays.toString(args));

            return method.invoke(mBase, args);
        }
    }

    public static void test(Context context) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        packageManager.getInstalledApplications(0);
    }
}
