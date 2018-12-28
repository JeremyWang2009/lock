package com.zfzjic.lock.dislock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by zfzjic on 18/12/28.
 */
@Component
public class RedissonDisLock {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * lock
     *
     * @param key
     * @param seconds
     * @return
     */
    public Boolean lock(String key, int seconds) throws Exception{
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock(seconds, TimeUnit.SECONDS);
    }

    public void unlock(String key) throws Exception{
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

}
