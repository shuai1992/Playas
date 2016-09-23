package com.zhongmeng.com.playmusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service {

    private MediaPlayer player;
    boolean isFinish;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isFinish=true;
            }
        });
    }
    public class MyBinder extends Binder implements Iplayer{

        @Override
        public void callPlay(String url) {
            play(url);
        }

        @Override
        public void callPause() {
            stop();
        }

        @Override
        public void callSeekProgress(int postion ) {
            setSeekProgress(postion);
        }
    }

    /**
     * 设置进度条进度
     */
    private void setSeekProgress(int postion) {
        if(player!=null){
            player.seekTo(postion);
        }
    }

    /**
     * 暂停
     */
    private void stop() {
        if(player!=null){
        player.pause();}
    }

    /**
     * 播放
     */
    private void play(String url) {
        //重置对象
        player.reset();
        try {
            //设置播放资源
            player.setDataSource(url);
            //资源异步准备
            player.prepareAsync();
            //准备好，进行播放
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    updateProgress();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新进度
     */
    private void updateProgress() {
        MyContant.sb.setProgress(player.getDuration());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isFinish){
                SystemClock.sleep(1000);
                MyContant.sb.setProgress(player.getCurrentPosition());}
            }
        });
    }


}
