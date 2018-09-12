package com.test.lock.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * Created by shanguang.wang on 18/9/10.
 */
@Component
public class DistributedLock {

    private final  String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * @param jedis redis client
     * @param key redis key
     * @param requestId uuid
     * @param time expire time in the units of expx
     * NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist.
     * EX|PX, expire time units: EX = seconds; PX = milliseconds
     */
    public boolean lock(Jedis jedis, String key, String requestId, int time) throws Exception{
        String statusCode = jedis.set(key, requestId, "NX", "EX", time);
        if("OK".equals(statusCode))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    /**
     * @param jedis
     * @param key
     * @param requestId
     * @throws Exception
     */
    public boolean unlock(Jedis jedis, String key, String requestId) throws Exception{
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(requestId));
        Long a = 1L;
        if(a.equals(result))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

}
