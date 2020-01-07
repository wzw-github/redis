package ocm.wzw.reids.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisPoolUtil {

    //jedisPool池
    private static volatile JedisPool jedisPool=null;
    private JedisPoolUtil(){

    }
    public static JedisPool getJedisPoolInstance(){
        if(null==jedisPool){
            synchronized (JedisPoolUtil.class){
                if(null==jedisPool){
                    //jedis池的配置
                    JedisPoolConfig poolConfig=new JedisPoolConfig();
                    //最大连接数
                    poolConfig.setMaxTotal(1000);
                    //最大空闲数
                    poolConfig.setMaxIdle(32);
                    //最大等待时间
                    poolConfig.setMaxWaitMillis(100*1000);
                    //连接时检查有效性
                    poolConfig.setTestOnBorrow(true);
                    jedisPool=new JedisPool(poolConfig,"192.168.1.104",6379);
                }
            }
        }
        return jedisPool;
    }

    public static void release(JedisPool jedisPool,Jedis jedis){
        if(null!=jedis){
            jedis.close();
        }
    }

}
