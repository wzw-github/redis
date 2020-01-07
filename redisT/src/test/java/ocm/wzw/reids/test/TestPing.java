package ocm.wzw.reids.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestPing {

    /**
     * 测试是否能连接到redis
     */
    @Test
    public void testPing(){
        Jedis jedis=new Jedis("192.168.43.223",6379);
        //连接成功jedis.ping是PONG
        System.out.println("___________________________________"+jedis.ping());

    }

}
