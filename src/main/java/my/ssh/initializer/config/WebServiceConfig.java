package my.ssh.initializer.config;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by zzy on 16/3/30.
 */
@Configuration
@ImportResource({"classpath:META-INF/cxf/cxf.xml","classpath:cxf-servlet.xml"})
@ComponentScan({"my.ssh.biz.*.*.ws.impl"})
public class WebServiceConfig {

    @Bean
    public LoggingInInterceptor inMessageInterceptor(){
        return new LoggingInInterceptor();
    }
    @Bean
    public LoggingOutInterceptor outMessageInterceptor(){
        return new LoggingOutInterceptor();
    }

}
