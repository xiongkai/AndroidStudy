package com.test.opensource;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.InMemoryDexClassLoader;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("NonConstantResourceId")
@Route(path = "/app/main")
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.textView) TextView textView;
    @Autowired
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(DexClassLoader.class);
        System.out.println(BaseDexClassLoader.class);
        System.out.println(InMemoryDexClassLoader.class);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //
        EventBus.getDefault().register(this);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //
        LeakCanary.install(getApplication());
        getSupportFragmentManager();
        //
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://www.baidu.com").build();
            Call call = client.newCall(request);
            Response response = call.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override public void subscribe(@NonNull ObservableEmitter<Object> emitter) { }
        }).map(new Function<Object, Object>() {
            @Override public Object apply(@NonNull Object o) { return null; }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
        //
        ARouter.init(getApplication());
        ARouter.getInstance().inject(this);
        //
        ImageView imageView = null;
        if (imageView != null){
            Glide.with(this).load("").into(imageView);
        }
        //
        RequestQueue requestQueue = Volley.newRequestQueue(this);
    }
}
