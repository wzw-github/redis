package com.wzw.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JedisClusterTest {
    public static void main(String[] args) throws IOException {
        Set<HostAndPort> nodes=new HashSet<>();
        //访问的ip和端口号，原本有三主三从，但是集群有重定向，所以写一个就可以了
        nodes.add(new HostAndPort("192.168.43.223",6380));
        JedisCluster cluster=new JedisCluster(nodes);
        cluster.set("username","admin");
        System.out.println("-------------------->"+cluster.get("username"));
        cluster.close();
    }
}
