package com.test.hookbasic.hooks;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.test.hookbasic.TestActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by Scott on 2020/12/13 0013
 */
public class HookActivityManagerService {

    public static void hook(){
        try {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityTaskManager");

            // 获取 gDefault 这个字段, 想办法替换它
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("IActivityTaskManagerSingleton");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            // 4.x以上的gDefault是一个 android.util.Singleton对象; 我们取出这个单例里面的字段
            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            // ActivityManagerNative 的gDefault对象里面原始的 IActivityManager对象
            Object rawIActivityManager = mInstanceField.get(gDefault);

            // 创建一个这个对象的代理对象, 然后替换这个字段, 让我们的代理对象帮忙干活
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityTaskManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iActivityManagerInterface}, new IActivityManagerHandler(rawIActivityManager));
            mInstanceField.set(gDefault, proxy);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    static class IActivityManagerHandler implements InvocationHandler {

        private Object mBase;

        public IActivityManagerHandler(Object base) {
            mBase = base;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e("xiongkai", "hey, baby; you are hooked!!");
            Log.e("xiongkai", "method:" + method.getName() + " called with args:" + Arrays.toString(args));

            return method.invoke(mBase, args);
        }
    }

    public static void test(Activity activity) {
        activity.startActivity(new Intent(activity, TestActivity.class));
    }
}
