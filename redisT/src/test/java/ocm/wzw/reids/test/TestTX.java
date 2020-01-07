package ocm.wzw.reids.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTX {

    @Test
    public void testTx(){
        Jedis jedis=new Jedis("192.168.43.223",6379);
        //打开事务
        Transaction transaction=jedis.multi();
        transaction.set("aa","123456");
        transaction.set("bb","778887");
        //提交事务
//        transaction.exec();
        //取消事务操作
        transaction.discard();

    }

    public boolean transMethod(){
        Jedis jedis=new Jedis("192.168.43.223",6379);
        int balance;//可以余额
        int debt;//欠额
        int amtToSubtract=10;//实刷额度
        jedis.watch("balance");
        jedis.set("balance","5");
        balance=Integer.parseInt(jedis.get("balance"));
        if(balance< amtToSubtract){
            jedis.unwatch();
            System.out.println("modify");
            return false;
        }else{
            System.out.println("*********************transaction");
            Transaction transaction=jedis.multi();
            transaction.decrBy("balance",amtToSubtract);
            transaction.incrBy("debt",amtToSubtract);
            transaction.exec();
            balance=Integer.parseInt(jedis.get("balance"));
            debt=Integer.parseInt(jedis.get("debt"));
            System.out.println("*****************"+balance);
            System.out.println("*****************"+debt);
            return true;
        }
    }

    @Test
    public void testTX1(){
        boolean retValue=transMethod();
        System.out.println("retValue:"+retValue);
    }
}
