package com.test.hookbasic.hooks;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.test.hookbasic.StubActivity;
import com.test.hookbasic.TestActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Scott on 2020/12/13 0013
 */
public class HookActivityManagerService {

    public static void hook(){
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
                Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");

                // 获取 gDefault 这个字段, 想办法替换它
                Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
                gDefaultField.setAccessible(true);
                Object gDefault = gDefaultField.get(null);

                // 4.x以上的gDefault是一个 android.util.Singleton对象; 我们取出这个单例里面的字段
                Class<?> singleton = Class.forName("android.util.Singleton");
                Field mInstanceField = singleton.getDeclaredField("mInstance");
                mInstanceField.setAccessible(true);

                // ActivityManagerNative 的gDefault对象里面原始的 IActivityManager对象
                Object rawIActivityManager = mInstanceField.get(gDefault);

                // 创建一个这个对象的代理对象, 然后替换这个字段, 让我们的代理对象帮忙干活
                Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
                Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{iActivityManagerInterface}, new IActivityManagerHandler(rawIActivityManager));
                mInstanceField.set(gDefault, proxy);
            }else{

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
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void hookHandlerCallback(){
        try{
            // 先获取到当前的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object currentActivityThread = currentActivityThreadField.get(null);

            // 由于ActivityThread一个进程只有一个,我们获取这个对象的mH
            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(currentActivityThread);

            Field mCallBackField = Handler.class.getDeclaredField("mCallback");
            mCallBackField.setAccessible(true);

            mCallBackField.set(mH, new ActivityThreadHandlerCallback(mH));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    static class ActivityThreadHandlerCallback implements Handler.Callback {
        Handler mBase;

        public ActivityThreadHandlerCallback(Handler mBase) {
            this.mBase = mBase;
        }

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                // ActivityThread里面 "LAUNCH_ACTIVITY" 这个字段的值是100
                // 本来使用反射的方式获取最好, 这里为了简便直接使用硬编码
                case 100:
                    Log.e("xiongkai", "hook handleMessage handleLaunchActivity");
                    handleLaunchActivity(msg);
                    break;
                case 159:
                    Log.e("xiongkai", "hook handleMessage ClientTransaction");
                    executeClientTransaction(msg);
                    break;
            }
            mBase.handleMessage(msg);
            return true;
        }

        private void handleLaunchActivity(Message msg) {
            // 这里简单起见,直接取出TargetActivity;
            Object obj = msg.obj;
            // 根据源码:
            // 这个对象是 ActivityClientRecord 类型
            // 我们修改它的intent字段为我们原来保存的即可.
            /*        switch (msg.what) {
            /             case LAUNCH_ACTIVITY: {
            /                 Trace.traceBegin(Trace.TRACE_TAG_ACTIVITY_MANAGER, "activityStart");
            /                 final ActivityClientRecord r = (ActivityClientRecord) msg.obj;
            /
            /                 r.packageInfo = getPackageInfoNoCheck(
            /                         r.activityInfo.applicationInfo, r.compatInfo);
            /                 handleLaunchActivity(r, null);
            */
            try {
                // 把替身恢复成真身
                Field intent = obj.getClass().getDeclaredField("intent");
                intent.setAccessible(true);
                Intent raw = (Intent) intent.get(obj);

                Intent target = raw.getParcelableExtra(HookHelper.EXTRA_TARGET_INTENT);
                raw.setComponent(target.getComponent());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        public void executeClientTransaction(Message msg){
            // 这里简单起见,直接取出TargetActivity;
            Object obj = msg.obj;
            // 根据源码:
            // 这个对象是 ActivityClientRecord 类型
            // 我们修改它的intent字段为我们原来保存的即可.
            /*        switch (msg.what) {
            /             case LAUNCH_ACTIVITY: {
            /                 Trace.traceBegin(Trace.TRACE_TAG_ACTIVITY_MANAGER, "activityStart");
            /                 final ActivityClientRecord r = (ActivityClientRecord) msg.obj;
            /
            /                 r.packageInfo = getPackageInfoNoCheck(
            /                         r.activityInfo.applicationInfo, r.compatInfo);
            /                 handleLaunchActivity(r, null);
            */
            try {
                // 把替身恢复成真身
                Field mActivityCallbacks = obj.getClass().getDeclaredField("mActivityCallbacks");
                mActivityCallbacks.setAccessible(true);
                List callbacks = (List) mActivityCallbacks.get(obj);
                for (Object callback : callbacks){
                    if (callback.getClass().getSimpleName().equals("LaunchActivityItem")){
                        Field intent = callback.getClass().getDeclaredField("mIntent");
                        intent.setAccessible(true);
                        Intent raw = (Intent) intent.get(callback);
                        Intent target = raw.getParcelableExtra(HookHelper.EXTRA_TARGET_INTENT);
                        raw.setComponent(target.getComponent());
                        break;
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
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
            if ("startActivity".equals(method.getName())) {
                // 只拦截这个方法
                // 替换参数, 任你所为;甚至替换原始Activity启动别的Activity偷梁换柱
                // API 23:
                // public final Activity startActivityNow(Activity parent, String id,
                // Intent intent, ActivityInfo activityInfo, IBinder token, Bundle state,
                // Activity.NonConfigurationInstances lastNonConfigurationInstances) {

                // 找到参数里面的第一个Intent 对象

                Intent raw;
                int index = 0;

                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {
                        index = i;
                        break;
                    }
                }
                raw = (Intent) args[index];

                Intent newIntent = new Intent();

                // 这里包名直接写死,如果再插件里,不同的插件有不同的包  传递插件的包名即可
                String targetPackage = "com.test.hookbasic";

                // 这里我们把启动的Activity临时替换为 StubActivity
                ComponentName componentName = new ComponentName(targetPackage, StubActivity.class.getCanonicalName());
                newIntent.setComponent(componentName);

                // 把我们原始要启动的TargetActivity先存起来
                newIntent.putExtra(HookHelper.EXTRA_TARGET_INTENT, raw);

                // 替换掉Intent, 达到欺骗AMS的目的
                args[index] = newIntent;

                Log.e("xiongkai", "hook startActivity success");
                return method.invoke(mBase, args);

            }
            return method.invoke(mBase, args);
        }
    }

    public static void test(Activity activity) {
        activity.startActivity(new Intent(activity, TestActivity.class));
    }

    public static class HookHelper{
        public static final String EXTRA_TARGET_INTENT = "EXTRA_TARGET_INTENT";
    }
}
