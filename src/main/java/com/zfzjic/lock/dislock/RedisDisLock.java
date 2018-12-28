package com.zfzjic.lock.dislock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.UUID;

/**
 * Created by zfzjic on 18/9/10.
 */
@Component
public class RedisDisLock {

    private final static String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private final static Long UNLOCK_RESULT = 1L;

    private final UUID uuid = UUID.randomUUID();

    @Autowired
    private JedisPool jedisPool;

    /**
     * @param key     redis key
     * @param seconds expire seconds in the units of expx
     *                NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist.
     *                EX|PX, expire time units: EX = seconds; PX = milliseconds
     */
    public boolean lock(String key, int seconds) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String statusCode = jedis.set(key, generateRequestId(), "NX", "EX", seconds);
        if ("OK".equals(statusCode))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    /**
     * @param key
     * @throws Exception
     */
    public boolean unlock(String key) throws Exception {
        Jedis jedis = jedisPool.getResource();
        Object result = jedis.eval(SCRIPT, Collections.singletonList(key), Collections.singletonList(generateRequestId()));
        if (UNLOCK_RESULT.equals(result))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    private String generateRequestId() {
        return uuid.toString() + ":" + Thread.currentThread().getId();
    }

}
