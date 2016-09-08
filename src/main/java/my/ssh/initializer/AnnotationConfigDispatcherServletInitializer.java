package my.ssh.initializer;

import my.ssh.initializer.config.*;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * springMVC配置
 */
@Order(3)
//spring DispatcherServlet的配置,其它servlet和监听器等需要额外声明，用@Order注解设定启动顺序
public class AnnotationConfigDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /*
      * DispatcherServlet的映射路径
	  */
    @Override
    protected String[] getServletMappings() {
        return new String[]{
                "/api/*"
        };
    }

    /*
      * 应用上下文，除web部分
	  */
    @Override
    protected Class[] getRootConfigClasses() {
        //加载配置文件类，这里与上面的xml配置是对应的，需要使用@Configuration注解进行标注，稍后介绍
        return new Class[]{
                SpringContextConfig.class,
                SpringSecurityConfig.class,
                SchedulingConfig.class,
                WebServiceConfig.class,
                RedisCacheConfig.class
        };
    }

    /*
	  * web上下文
	  */
    @Override
    protected Class[] getServletConfigClasses() {
        return new Class[]{
                SpringMvcConfig.class,
                WebSocketConfig.class
        };
    }

    /*
     * 注册过滤器，映射路径与DispatcherServlet一致，路径不一致的过滤器需要注册到另外的WebApplicationInitializer中
     */
    @Override
    protected Filter[] getServletFilters() {

        //spring字符转码过滤器
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        return new Filter[]{
                new HttpPutFormContentFilter(),//restful风格put类型传参支持
                characterEncodingFilter
        };
    }

}

