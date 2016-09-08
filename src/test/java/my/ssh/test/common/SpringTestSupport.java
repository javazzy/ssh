package my.ssh.test.common;


import my.ssh.initializer.config.SpringContextConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Spring测试支持,用于测试由Spring 管理的bean,编写测试类时,继承该类
 */
@ContextConfiguration(classes={SpringContextConfig.class})
public class SpringTestSupport extends AbstractTransactionalJUnit4SpringContextTests {

	public <T> T getBean(Class<T> type) {
		return applicationContext.getBean(type);
	}

	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public ApplicationContext getContext() {
		return applicationContext;
	}

}