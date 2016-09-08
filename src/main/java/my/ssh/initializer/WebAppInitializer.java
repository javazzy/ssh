package my.ssh.initializer;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.util.IntrospectorCleanupListener;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by zzy on 16/3/25.
 */
@Order(1)
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {

        servletContext.setInitParameter("webAppRootKey", "webapp.root");

        //载入log4j配置文件
        servletContext.setInitParameter("log4jConfigLocation", "classpath:log4j.properties");

        //Spring默认刷新Log4j配置文件的间隔,单位为millisecond
        servletContext.setInitParameter("log4jRefreshInterval", "1000");

        //Spring log4j Config loader
        servletContext.addListener("org.springframework.web.util.Log4jConfigListener");

        //Spring 刷新Introspector防止内存泄露
        servletContext.addListener(IntrospectorCleanupListener.class);

        //CXF配置
        ServletRegistration.Dynamic servletDynamic = servletContext.addServlet("jaxws-servlet", CXFServlet.class);
//        servletDynamic.setLoadOnStartup(1);
        servletDynamic.addMapping("/ws/*");


        //开启session过滤器
        OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("openSessionInViewFilter", openSessionInViewFilter);
        filterRegistration.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE),
                false,
                "/api/*"
        );
        filterRegistration.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE),
                false,
                "/j_spring_security_check"
        );
//
//        //spring字符转码过滤器
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//        filterRegistration = servletContext.addFilter("characterEncodingFilter", characterEncodingFilter);
//        filterRegistration.addMappingForUrlPatterns(
//                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE),
//                false,
//                "/*"
//        );

    }


}