package my.ssh.initializer.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ApplicationConfig Spring配置类
 */
@Configuration
public class SshDbConfig {
    private static final Logger logger = Logger.getLogger(SshDbConfig.class);
    @Resource
    private DataSource dataSource;
    @Resource
    private SessionFactory sessionFactory;

    private Properties p = new Properties();

    public SshDbConfig() {
        try {
            p.load(this.getClass().getResourceAsStream("/jdbc/jdbc-default.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws IOException, SQLException {
        logger.info("dataSource");
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();

        Properties properties = new Properties();
        properties.setProperty("url", p.getProperty("jdbc.url"));
        properties.setProperty("user", p.getProperty("jdbc.user"));
        properties.setProperty("password", p.getProperty("jdbc.password"));
        dataSource.setXaProperties(properties);

        dataSource.setUniqueResourceName(p.getProperty("jdbc.uniqueResourceName"));
        dataSource.setXaDataSourceClassName(p.getProperty("jdbc.xaDataSourceClassName"));
        dataSource.setMinPoolSize(Integer.parseInt(p.getProperty("jdbc.minPoolSize")));
        dataSource.setMaxPoolSize(Integer.parseInt(p.getProperty("jdbc.maxPoolSize")));
        dataSource.setMaxIdleTime(Integer.parseInt(p.getProperty("jdbc.maxIdleTime")));
        dataSource.setMaintenanceInterval(Integer.parseInt(p.getProperty("jdbc.maintenanceInterval")));
        dataSource.setBorrowConnectionTimeout(Integer.parseInt(p.getProperty("jdbc.borrowConnectionTimeout")));
        dataSource.setReapTimeout(Integer.parseInt(p.getProperty("jdbc.reapTimeout")));
        dataSource.setTestQuery(p.getProperty("jdbc.TestQuery"));

        return dataSource;
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory() throws Exception {
        logger.info("sessionFactory");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(new String[]{p.getProperty("hibernate.entity.package")});

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", p.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", p.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.format_sql", p.getProperty("hibernate.format_sql"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", p.getProperty("hibernate.hbm2ddl.auto"));
//        hibernateProperties.setProperty("hibernate.transaction.coordinator_class", p.getProperty("hibernate.transaction.coordinator_class"));

        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", p.getProperty("hibernate.jdbc.fetch_size"));
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", p.getProperty("hibernate.jdbc.batch_size"));
        hibernateProperties.setProperty("hibernate.generate_statistics", p.getProperty("hibernate.generate_statistics"));

//        hibernateProperties.setProperty("hibernate.cache.use_query_cache", p.getProperty("hibernate.cache.use_query_cache"));
//        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", p.getProperty("hibernate.cache.use_second_level_cache"));
//        hibernateProperties.setProperty("hibernate.cache.region.factory_class", p.getProperty("hibernate.cache.region.factory_class"));
//        hibernateProperties.setProperty("hibernate.cache.provider_configuration_file_resource_path", p.getProperty("hibernate.cache.provider_configuration_file_resource_path"));
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