package com.test.widgetslistview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    static class TestViewModel extends ViewModel{
        public TestViewModel(SavedStateHandle handle){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this)).get(TestViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listview);
        final MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.notifyDataSetChanged();
            }
        });
        Log.e("xiongkai", "onCreate");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("xiongkai", "onSaveInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("xiongkai", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("xiongkai", "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("xiongkai", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("xiongkai", "onPause");
    }
}
