package com.wangzh.circlewaveprogress;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangzh.circlewaveprogress.wave.WaveHelper;
import com.wangzh.circlewaveprogress.wave.WaveView;

public class MainActivity extends AppCompatActivity {

    private WaveHelper mWaveHelper;
    private int mBorderColor = Color.parseColor("#44FFFFFF");
    private int mBorderWidth = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* final WaveView waveView = (WaveView) findViewById(R.id.wave);
        waveView.setBorder(mBorderWidth, mBorderColor);
        mWaveHelper = new WaveHelper(waveView);
        mWaveHelper.start();*/
    }
}
