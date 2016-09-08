package my.ssh.initializer.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.apache.log4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Resource;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * ApplicationConfig Spring配置类
 */
@Configuration
@ComponentScan({"my.ssh.biz.*.*.dao.impl", "my.ssh.biz.*.*.service.impl"})
@PropertySource({"classpath:jdbc/jdbc-default.properties"})
@Import({SshDbConfig.class/*,TestDbConfig.class*/})
@EnableTransactionManagement
//@EnableCaching
public class SpringContextConfig {
    private static final Logger logger = Logger.getLogger(SpringContextConfig.class);

    @Resource
    private Environment env;

    @Resource
    private TransactionManager atomikosTransactionManager;
    @Resource
    private UserTransaction atomikosUserTransaction;
//    @Resource
//    private net.sf.ehcache.CacheManager ehCacheManagerFactory;

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager(){
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }

    @Bean
    public UserTransactionImp atomikosUserTransaction() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(300);
        return userTransactionImp;
    }

    @Bean
    public JtaTransactionManager springTransactionManager() {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(atomikosTransactionManager);
        jtaTransactionManager.setUserTransaction(atomikosUserTransaction);
        jtaTransactionManager.setAllowCustomIsolationLevels(true);
        return jtaTransactionManager;
    }

//    /**
//     * spring缓存
//     * @return
//     */
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactory() {
//        EhCacheManagerFactoryBean cacheManagerFactory = new EhCacheManagerFactoryBean();
//        cacheManagerFactory.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        return cacheManagerFactory;
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
//        cacheManager.setCacheManager(ehCacheManagerFactory);
//        return cacheManager;
//    }

    /**
     * 国际化处理
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
        bean.setBasename("message.messages");
        bean.setCacheSeconds(3000);
        bean.setUseCodeAsDefaultMessage(true);
        return bean;
    }
}