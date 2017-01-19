package my.ssh.initializer.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import my.ssh.util.PropertiesUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ApplicationConfig Spring配置类
 */
@Configuration
@PropertySource(value = "classpath:/config.properties", ignoreResourceNotFound = true)
public class SshDbConfig {
    private static final Logger logger = Logger.getLogger(SshDbConfig.class);

    private final String JDBC_PROP_FILE_KEY = "prop.jdbc.local";
    private final String DEFAULT_JDBC_PROP_FILE = "/jdbc/jdbc-local.properties";

    @Resource
    private Environment env;
    @Resource
    private Properties jdbcProp;
    @Resource
    private DataSource dataSource;
    @Resource
    private SessionFactory sessionFactory;

    @Bean
    public Properties jdbcProp() throws IOException {
        return PropertiesUtils.loadProperties(env.getProperty(JDBC_PROP_FILE_KEY),DEFAULT_JDBC_PROP_FILE);
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @DependsOn("jdbcProp")
    public DataSource dataSource() throws IOException, SQLException {
        logger.info("dataSource");
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();

        Properties properties = new Properties();
        properties.setProperty("url", jdbcProp.getProperty("jdbc.url"));
        properties.setProperty("user", jdbcProp.getProperty("jdbc.user"));
        properties.setProperty("password", jdbcProp.getProperty("jdbc.password"));
        dataSource.setXaProperties(properties);

        dataSource.setUniqueResourceName(jdbcProp.getProperty("jdbc.uniqueResourceName"));
        dataSource.setXaDataSourceClassName(jdbcProp.getProperty("jdbc.xaDataSourceClassName"));
        dataSource.setMinPoolSize(Integer.parseInt(jdbcProp.getProperty("jdbc.minPoolSize")));
        dataSource.setMaxPoolSize(Integer.parseInt(jdbcProp.getProperty("jdbc.maxPoolSize")));
        dataSource.setMaxIdleTime(Integer.parseInt(jdbcProp.getProperty("jdbc.maxIdleTime")));
        dataSource.setMaintenanceInterval(Integer.parseInt(jdbcProp.getProperty("jdbc.maintenanceInterval")));
        dataSource.setBorrowConnectionTimeout(Integer.parseInt(jdbcProp.getProperty("jdbc.borrowConnectionTimeout")));
        dataSource.setReapTimeout(Integer.parseInt(jdbcProp.getProperty("jdbc.reapTimeout")));
        dataSource.setTestQuery(jdbcProp.getProperty("jdbc.testQuery"));

        return dataSource;
    }


    @Bean
    @DependsOn("jdbcProp")
    public LocalSessionFactoryBean sessionFactory() throws Exception {
        logger.info("sessionFactory");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(new String[]{jdbcProp.getProperty("hibernate.entity.package")});

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", jdbcProp.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", jdbcProp.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.format_sql", jdbcProp.getProperty("hibernate.format_sql"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", jdbcProp.getProperty("hibernate.hbm2ddl.auto"));
//        hibernateProperties.setProperty("hibernate.transaction.coordinator_class", jdbcProp.getProperty("hibernate.transaction.coordinator_class"));

        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", jdbcProp.getProperty("hibernate.jdbc.fetch_size"));
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", jdbcProp.getProperty("hibernate.jdbc.batch_size"));
        hibernateProperties.setProperty("hibernate.generate_statistics", jdbcProp.getProperty("hibernate.generate_statistics"));

//        hibernateProperties.setProperty("hibernate.cache.use_query_cache", jdbcProp.getProperty("hibernate.cache.use_query_cache"));
//        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", jdbcProp.getProperty("hibernate.cache.use_second_level_cache"));
//        hibernateProperties.setProperty("hibernate.cache.region.factory_class", jdbcProp.getProperty("hibernate.cache.region.factory_class"));
//        hibernateProperties.setProperty("hibernate.cache.provider_configuration_file_resource_path", jdbcProp.getProperty("hibernate.cache.provider_configuration_file_resource_path"));
        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }

    @Bean
    public HibernateTemplate hibernateTemplate() throws Exception {
        HibernateTemplate hibernateTemplate = new HibernateTemplate();
        hibernateTemplate.setSessionFactory(sessionFactory);
        return hibernateTemplate;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource);
    }
}