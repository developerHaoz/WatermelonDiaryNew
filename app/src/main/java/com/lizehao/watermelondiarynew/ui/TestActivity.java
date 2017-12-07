package com.lizehao.watermelondiarynew.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lizehao.watermelondiarynew.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by developerHaoz on 2017/5/11.
 */

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @Bind(R.id.button1)
    Button mButton1;
    @Bind(R.id.button2)
    Button mButton2;
    @Bind(R.id.button3)
    Button mButton3;
    @Bind(R.id.button4)
    Button mButton4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Log.d(TAG, "onViewClicked: You Click button1");
                break;
            case R.id.button2:
                Log.d(TAG, "onViewClicked: You Click button2");
                break;
            case R.id.button3:
                Log.d(TAG, "onViewClicked: You Click button3");
                break;
            case R.id.button4:
                Log.d(TAG, "onViewClicked: You Click button4");
                break;
        }
    }
}
