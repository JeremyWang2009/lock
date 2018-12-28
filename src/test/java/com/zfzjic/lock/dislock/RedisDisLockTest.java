package com.zfzjic.lock.dislock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zfzjic on 18/9/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class RedisDisLockTest {

    @Autowired
    private RedisDisLock redisDisLock;

    /**
     * handle same thread lock and unlock
     * @throws Exception
     */
    @Test
    public void testSameThread() throws Exception {
        String key = "user:001";
        try {
            redisDisLock.lock(key, 5);
            Thread.sleep(3000);     // mock handle task
        } finally {
            Assert.assertTrue(redisDisLock.unlock(key));
        }
    }

    /**
     * handle different thread lock and unlock
     * @throws Exception
     */
    @Test
    public void testDiffentThread() throws Exception {
        final String key = "user:001";
        try {
            redisDisLock.lock(key, 5);
            // mock handle task
            Thread.sleep(3000);
        } finally {
            Runnable runnable = () ->{
                try {
                    Assert.assertTrue(redisDisLock.unlock(key));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(runnable).start();
        }
    }

}
