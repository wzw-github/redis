package ocm.wzw.reids.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Random;

/**
 *测试pool
 */
public class TestPool {

    JedisPool jedisPool=JedisPoolUtil.getJedisPoolInstance();
    Jedis jedis=null;

    @Test
    public void testPool(){
        jedis=jedisPool.getResource();
        jedis.set("a1","a11");
        JedisPoolUtil.release(jedisPool,jedis);
    }

    @Test
    public void testRandom(){
        int rNumber=new Random().nextInt(100);
        System.out.println(rNumber);
    }

}
