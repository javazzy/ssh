package my.ssh.initializer.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import javax.annotation.Resource;

@Configuration
@EnableCaching
@EnableRedisHttpSession
@PropertySource({"classpath:redis.properties"})
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Resource
    private Environment env;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory;
        boolean sentinelEnabled = Boolean.parseBoolean(env.getProperty("redis.sentinelEnabled"));
        String redisNodes = env.getProperty("redis.nodes");

        if(sentinelEnabled) {
            RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();

            for (String connInfoStr : redisNodes.split(",")) {
                String[] connInfoArr = connInfoStr.split(":");
                RedisNode redisNode = new RedisNode(connInfoArr[0], Integer.parseInt(connInfoArr[1]));
                redisSentinelConfiguration.addSentinel(redisNode);
            }
            redisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration);
        }else{
            String[] connInfoArr = redisNodes.split(":");
            JedisShardInfo jedisShardInfo = new JedisShardInfo(connInfoArr[0],Integer.parseInt(connInfoArr[1]));
            redisConnectionFactory = new JedisConnectionFactory(jedisShardInfo);
        }

        if(StringUtils.isNotBlank(env.getProperty("redis.usePool")) && Boolean.parseBoolean(env.getProperty("redis.usePool"))) {
            redisConnectionFactory.setUsePool(true);

            JedisPoolConfig poolConfig = new JedisPoolConfig();

            //最大空闲连接数, 默认8个
            if (StringUtils.isNotBlank(env.getProperty("redis.maxIdle")))
                poolConfig.setMaxIdle(Integer.parseInt(env.getProperty("redis.maxIdle")));

            //最大连接数, 默认8个
            if (StringUtils.isNotBlank(env.getProperty("redis.maxTotal")))
                poolConfig.setMaxTotal(Integer.parseInt(env.getProperty("redis.maxTotal")));

            //最小空闲连接数, 默认0
            if (StringUtils.isNotBlank(env.getProperty("redis.minIdle")))
                poolConfig.setMaxIdle(Integer.parseInt(env.getProperty("redis.minIdle")));

            //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
            if (StringUtils.isNotBlank(env.getProperty("redis.maxWaitMillis")))
                poolConfig.setMaxWaitMillis(Long.parseLong(env.getProperty("redis.maxWaitMillis")));

            //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
            if (StringUtils.isNotBlank(env.getProperty("redis.blockWhenExhausted")))
                poolConfig.setBlockWhenExhausted(Boolean.parseBoolean(env.getProperty("redis.blockWhenExhausted")));

            //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
            if (StringUtils.isNotBlank(env.getProperty("redis.evictionPolicyClassName")))
                poolConfig.setEvictionPolicyClassName(env.getProperty("redis.evictionPolicyClassName"));

            //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
            if (StringUtils.isNotBlank(env.getProperty("redis.minEvictableIdleTimeMillis")))
                poolConfig.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("redis.minEvictableIdleTimeMillis")));

            //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
            if (StringUtils.isNotBlank(env.getProperty("redis.softMinEvictableIdleTimeMillis")))
                poolConfig.setSoftMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("redis.softMinEvictableIdleTimeMillis")));

            //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
            if (StringUtils.isNotBlank(env.getProperty("redis.numTestsPerEvictionRun")))
                poolConfig.setNumTestsPerEvictionRun(Integer.parseInt(env.getProperty("redis.numTestsPerEvictionRun")));

            //是否启用pool的jmx管理功能, 默认true
            if (StringUtils.isNotBlank(env.getProperty("redis.jmxEnabled")))
                poolConfig.setJmxEnabled(Boolean.parseBoolean(env.getProperty("redis.jmxEnabled")));

            //是否启用后进先出, 默认true
            if (StringUtils.isNotBlank(env.getProperty("redis.lifo")))
                poolConfig.setLifo(Boolean.parseBoolean(env.getProperty("redis.lifo")));

            //在获取连接的时候检查有效性, 默认false
            if (StringUtils.isNotBlank(env.getProperty("redis.testOnBorrow")))
                poolConfig.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("redis.testOnBorrow")));

            //在空闲时检查有效性, 默认false
            if (StringUtils.isNotBlank(env.getProperty("redis.testWhileIdle")))
                poolConfig.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("redis.testWhileIdle")));

            //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
            if (StringUtils.isNotBlank(env.getProperty("redis.timeBetweenEvictionRunsMillis")))
                poolConfig.setTimeBetweenEvictionRunsMillis(Integer.parseInt(env.getProperty("redis.timeBetweenEvictionRunsMillis")));

            redisConnectionFactory.setPoolConfig(poolConfig);
        }
        return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)
        return cacheManager;
    }
}