package com.wzw.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

@WebServlet("/doseckill")
public class SecKillServlet extends HttpServlet {

    JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
    Jedis jedis = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        jedis = jedisPool.getResource();

        // TODO Auto-generated method stub
        //模拟不同的用户id
        String userid = new Random().nextInt(50000) + "";
        //商品id
        String prodid = request.getParameter("prodid");

        String a = request.getParameter("a");

        //商品的库存
        String prodidKC="prodid:"+prodid+":kc";
        //进行了秒杀的用户
        String userId="prodid:"+prodid+":user";
        //监视库存
        jedis.watch(prodidKC);
        //获取库存
        String kc=jedis.get(prodidKC);

        try {
            //如果库存为null
            if(kc==null){
                System.out.println("---------------------->秒杀没有开始！");
                response.getWriter().print(true);
                return;
            }
            //如果库存小于等于0
            if(Integer.parseInt(kc)<=0){
                System.out.println("---------------------->已经被抢光了！");
                response.getWriter().print(false);
                return;
            }
            //查看用户是不是已经秒杀过了
            if(jedis.sismember(userId,userid)){
                System.out.println("------------------>该用户已经秒杀过了");
                response.getWriter().print(true);
                return;
            }

            //排除异常的情况，以下就开始执行事务，因为要避免中途被修改，采用事务提交
            Transaction transaction=jedis.multi();
            transaction.decr(prodidKC);
            transaction.sadd(userId,userid);

            //如果提交的事务成功了，返回值不为null或长度不为0
            List<Object> execs=transaction.exec();
            if(execs==null||execs.size()==0){
                System.out.println("-------------->秒杀失败");
                response.getWriter().print(false);
            }

            //如果没有异常，则秒杀成功
            System.out.println("------------->秒杀成功");
            response.getWriter().print(true);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            jedis.close();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("-------------doGet----------------");
    }


    private void allKey() {
        jedis = jedisPool.getResource();
        System.out.println("===================连接状态:" + jedis.ping());
        Set<String> allKey = jedis.keys("*");
        System.out.println("--------------------------->allKey:" + allKey);
    }

}
