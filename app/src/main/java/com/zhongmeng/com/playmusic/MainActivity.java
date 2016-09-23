package com.zhongmeng.com.playmusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button tv1;
    private Button tv2;
    private SeekBar sb;
    private Iplayer play;
    private MyServiceConn conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent=new Intent(this,MusicService.class);
        startService(intent);
        conn = new MyServiceConn();
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    private void initView() {
        tv1 = (Button) findViewById(R.id.tv1);
        tv2 = (Button) findViewById(R.id.tv2);
        sb = (SeekBar) findViewById(R.id.sb);
        MyContant.sb=sb;
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    play.callSeekProgress(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                play.callPlay("12");
                break;
            case R.id.tv2:
                play.callPause();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        //在退出时解绑
        unbindService(conn);
        super.onDestroy();
    }
    public class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            play = (Iplayer)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
