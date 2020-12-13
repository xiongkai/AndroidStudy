package com.test.hookbasic.hooks;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.test.hookbasic.MainActivity;
import com.test.hookbasic.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import androidx.core.app.NotificationCompat;

/**
 * Created by Scott on 2020/12/13 0013
 */
public class HookNotificationManager {

    public static void hook(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // 得到系统的 sService
            Method getService = NotificationManager.class.getDeclaredMethod("getService");
            getService.setAccessible(true);
            final Object sService = getService.invoke(notificationManager);

            Class iNotiMngClz = Class.forName("android.app.INotificationManager");
            // 动态代理 INotificationManager
            Object proxyNotiMng = Proxy.newProxyInstance(iNotiMngClz.getClassLoader(), new Class[]{iNotiMngClz}, new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.e("xiongkai", "invoke(). method:{}"+method.getName());
                    if (args != null && args.length > 0) {
                        for (Object arg : args) {
                            Log.e("xiongkai", "type:{}, arg:{}"+ (arg != null ? arg.getClass() : null)+"---"+arg);
                        }
                    }
                    // 操作交由 sService 处理，不拦截通知
                    return method.invoke(sService, args);
                    // 拦截通知，什么也不做
                    //return null;
                    // 或者是根据通知的 Tag 和 ID 进行筛选
                }
            });
            // 替换 sService
            Field sServiceField = NotificationManager.class.getDeclaredField("sService");
            sServiceField.setAccessible(true);
            sServiceField.set(notificationManager, proxyNotiMng);
        } catch (Exception e) {
            Log.e("xiongkai", "Hook NotificationManager failed!", e);
        }
    }

    public static void test(Context context){
        Notification notification =
                new NotificationCompat.Builder(context, "test_hook_notification")
                        .setSmallIcon(R.mipmap.ic_launcher).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
