package com.test.opensource;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.textView) TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        }catch (Exception ex){ }
        //
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override public void subscribe(ObservableEmitter<Object> emitter) { }
        }).map(new Function<Object, Object>() {
            @Override public Object apply(Object o) { return null; }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
        //
        ARouter.init(getApplication());
    }
}
