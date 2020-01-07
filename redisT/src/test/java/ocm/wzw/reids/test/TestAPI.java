package ocm.wzw.reids.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestAPI {

    @Test
    public void testAPI(){
        Jedis jedis=new Jedis("192.168.43.223",6379);

        //String
        jedis.set("testString","测试String");
        System.out.println("存值："+jedis.get("testString"));
        jedis.append("testString",",测试拼接");
        System.out.println("测试拼接："+jedis.get("testString"));
        System.out.println("长度"+jedis.strlen("testString"));
        jedis.del("testString");
        System.out.println(jedis.get("testString"));
        System.out.println("========================================");
        jedis.set("testStringIncr","1");
        jedis.incr("testStringIncr");
        System.out.println("原值1，加过后的值："+jedis.get("testStringIncr"));

    }
}
