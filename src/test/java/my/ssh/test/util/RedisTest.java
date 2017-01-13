package my.ssh.test.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zzy on 2017/1/13.
 */
public class RedisTest {

    String redisNodes = "192.168.1.104:7000,192.168.1.104:7001,192.168.1.104:7002,192.168.1.104:7003,192.168.1.104:7004,192.168.1.104:7005";
    private JedisCluster jedisCluster;
    private GenericObjectPoolConfig genericObjectPoolConfig;
    private Integer timeout;
    private Integer maxRedirections;

    @Test
    public void testRedis() throws Exception{

        genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(100);
        genericObjectPoolConfig.setMaxTotal(1000);
        genericObjectPoolConfig.setMinIdle(8);
        genericObjectPoolConfig.setMaxWaitMillis(-1);


        timeout = 300000;

        maxRedirections = 6;

        Set<HostAndPort> hostAndPortSet = new HashSet<>();

        for (String connInfoStr : redisNodes.split(",")) {
            String[] host_port = connInfoStr.split(":");
            String host = host_port[0];
            int port = Integer.parseInt(host_port[1]);
            HostAndPort hostAndPort = new HostAndPort(host,port);
            hostAndPortSet.add(hostAndPort);
        }
        jedisCluster = new JedisCluster(hostAndPortSet,timeout,maxRedirections,genericObjectPoolConfig);

        jedisCluster.set("aaa","bbb");

    }




}
