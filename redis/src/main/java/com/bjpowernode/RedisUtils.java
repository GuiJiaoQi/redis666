package com.bjpowernode;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
   //定义连接池对象
    private static JedisPool pool = null;
    //创建连接池
    public static JedisPool open(String host,int port){
        //如果连接池里面是空的话
        if (pool == null){
            //就创建连接池的配置对象
            JedisPoolConfig config = new JedisPoolConfig();
            //连接池最大连接数
            config.setMaxTotal(10);
            //最大空闲连接,保证有任务可以马上得到连接
            config.setMaxIdle(2);
            //检查连接池是否有密码
            config.setTestOnBorrow(true);

            pool = new JedisPool(config,host,port);
        }
        return pool;
    }
    //关闭连接池
    public static void close(){
        if (pool == null){
            pool.close();
        }
    }
    }