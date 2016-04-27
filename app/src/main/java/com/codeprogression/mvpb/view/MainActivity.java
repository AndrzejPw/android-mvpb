package com.codeprogression.mvpb.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.codeprogression.mvpb.R;
import com.codeprogression.mvpb.core.MyApplication;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.get(this).getAppComponent().inject(this);

        setContentView(R.layout.main_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        component = null;
    }
}
