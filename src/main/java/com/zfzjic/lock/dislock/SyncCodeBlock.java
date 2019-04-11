package com.zfzjic.lock.dislock;

/**
 * Created by shanguang.wang on 2019-03-31
 */
public class SyncCodeBlock {

    public int i;

    public void syncTask() {
        // 同步代码库
        synchronized (this) {
            i++;
        }
    }
}
