package my.ssh.initializer.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.io.IOException;

@Configuration
@EnableCaching
@EnableRedisHttpSession
public class RedisCacheConfig extends CachingConfigurerSupport {

    private final String SPRING_REDIS_CONFIG_PROPERTIES = "redis.properties";
    @Resource
    private PropertySource propertySource;
    @Resource
    private JedisConnectionFactory jedisConnectionFactory;
    @Resource
    private RedisTemplate redisTemplate;

    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大空闲连接数, 默认8个
        if (propertySource.containsProperty("spring.redis.pool.maxIdle"))
            jedisPoolConfig.setMaxIdle(Integer.parseInt(propertySource.getProperty("spring.redis.pool.maxIdle").toString()));

        //最大连接数, 默认8个
        if (propertySource.containsProperty("spring.redis.pool.maxTotal"))
            jedisPoolConfig.setMaxTotal(Integer.parseInt(propertySource.getProperty("spring.redis.pool.maxTotal").toString()));

        //最小空闲连接数, 默认0
        if (propertySource.containsProperty("spring.redis.pool.minIdle"))
            jedisPoolConfig.setMaxIdle(Integer.parseInt(propertySource.getProperty("spring.redis.pool.minIdle").toString()));

        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        if (propertySource.containsProperty("spring.redis.pool.maxWaitMillis"))
            jedisPoolConfig.setMaxWaitMillis(Long.parseLong(propertySource.getProperty("spring.redis.pool.maxWaitMillis").toString()));

        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        if (propertySource.containsProperty("spring.redis.pool.blockWhenExhausted"))
            jedisPoolConfig.setBlockWhenExhausted(Boolean.parseBoolean(propertySource.getProperty("spring.redis.pool.blockWhenExhausted").toString()));

        //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        if (propertySource.containsProperty("spring.redis.pool.evictionPolicyClassName"))
            jedisPoolConfig.setEvictionPolicyClassName(propertySource.getProperty("spring.redis.pool.evictionPolicyClassName").toString());

        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        if (propertySource.containsProperty("spring.redis.pool.minEvictableIdleTimeMillis"))
            jedisPoolConfig.setMinEvictableIdleTimeMillis(Long.parseLong(propertySource.getProperty("spring.redis.pool.minEvictableIdleTimeMillis").toString()));

        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        if (propertySource.containsProperty("spring.redis.pool.softMinEvictableIdleTimeMillis"))
            jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(Long.parseLong(propertySource.getProperty("spring.redis.pool.softMinEvictableIdleTimeMillis").toString()));

        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        if (propertySource.containsProperty("spring.redis.pool.numTestsPerEvictionRun"))
            jedisPoolConfig.setNumTestsPerEvictionRun(Integer.parseInt(propertySource.getProperty("spring.redis.pool.numTestsPerEvictionRun").toString()));

        //是否启用pool的jmx管理功能, 默认true
        if (propertySource.containsProperty("spring.redis.pool.jmxEnabled"))
            jedisPoolConfig.setJmxEnabled(Boolean.parseBoolean(propertySource.getProperty("spring.redis.pool.jmxEnabled").toString()));

        //是否启用后进先出, 默认true
        if (propertySource.containsProperty("spring.redis.pool.lifo"))
            jedisPoolConfig.setLifo(Boolean.parseBoolean(propertySource.getProperty("spring.redis.pool.lifo").toString()));

        //在获取连接的时候检查有效性, 默认false
        if (propertySource.containsProperty("spring.redis.pool.testOnBorrow"))
            jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(propertySource.getProperty("spring.redis.pool.testOnBorrow").toString()));

        //在空闲时检查有效性, 默认false
        if (propertySource.containsProperty("spring.redis.pool.testWhileIdle"))
            jedisPoolConfig.setTestWhileIdle(Boolean.parseBoolean(propertySource.getProperty("spring.redis.pool.testWhileIdle").toString()));

        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        if (propertySource.containsProperty("spring.redis.pool.timeBetweenEvictionRunsMillis"))
            jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Integer.parseInt(propertySource.getProperty("spring.redis.pool.timeBetweenEvictionRunsMillis").toString()));
        return jedisPoolConfig;
    }

    @Bean
    public ResourcePropertySource propertySource() throws IOException {
        return new ResourcePropertySource(new ClassPathResource(SPRING_REDIS_CONFIG_PROPERTIES));
    }

    public RedisSentinelConfiguration redisSentinelConfiguration(){
        return new RedisSentinelConfiguration(propertySource);
    }

    public RedisClusterConfiguration redisClusterConfiguration(){
        return new RedisClusterConfiguration(propertySource);
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory;
        boolean redisClusterEnabled = Boolean.parseBoolean(propertySource.getProperty("spring.redis.cluster.enabled").toString());

        if(redisClusterEnabled) {
            redisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration(),jedisPoolConfig());
        }else{
            redisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration(),jedisPoolConfig());
        }

        return redisConnectionFactory;
    }

    /**
     * 配置redis管理的session失效时间
     * @return
     */
    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration(){
        RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
        redisHttpSessionConfiguration.setMaxInactiveIntervalInSeconds(1800);//30分钟（60*30）
        return redisHttpSessionConfiguration;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        //<!--如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！！  -->
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)
        return cacheManager;
    }
}