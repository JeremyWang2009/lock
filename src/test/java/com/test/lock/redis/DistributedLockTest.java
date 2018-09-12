package com.test.lock.redis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

/**
 * Created by shanguang.wang on 18/9/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class DistributedLockTest {

    @Autowired
    private DistributedLock distributedLock;

    private Jedis jedis;
    private static final String HOST_NAME = "127.0.0.1";
    private static final int PORT = 6379;
    private static final String PASSWORD = "123456";


    @Before
    public void init(){
        jedis = new Jedis(HOST_NAME, PORT);
        jedis.auth(PASSWORD);
    }

    @Test
    public void lockTest() throws Exception{
        String key = "user:001";
        String requestId = "UAF23E23NADSFASFD";
        distributedLock.lock(jedis, key, requestId, 860);
    }

    @Test
    public void unlockTest() throws Exception{
        String key = "user:001";
        String requestId = "UAF23E23NADSFASFD";
        distributedLock.unlock(jedis, key, requestId);
    }
}
