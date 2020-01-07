package ocm.wzw.reids.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * 测试主从复制
 */
public class TestMS {

    @Test
    public void testMS(){
        Jedis jedis_M=new Jedis("192.168.43.223",6379);
        Jedis jedis_S=new Jedis("192.168.43.223",6380);

        jedis_S.slaveof("192.168.43.223",6379);

        jedis_M.set("class","10201");

        System.out.println("class==========="+jedis_S.get("class"));
    }

}
