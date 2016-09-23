package com.zhongmeng.com.playmusic;

/**
 * Created by 天地 on 2016/8/14.
 */
public interface Iplayer {
    void callPlay(String url);
    void callPause();
    void callSeekProgress(int progress);
}
