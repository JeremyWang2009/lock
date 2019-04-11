package com.zfzjic.lock.dislock;

/**
 * Created by shanguang.wang on 2019-03-31
 */
public class VolatileTest {

    private  volatile Integer visible = 1;

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public static void main(String[] args) {
    }
}