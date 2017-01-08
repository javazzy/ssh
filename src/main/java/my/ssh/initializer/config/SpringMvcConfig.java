package my.ssh.initializer.config;

import my.ssh.security.SecurityURLInterceptor;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Spring3.1基于注解的配置类， 用于代替原来的<b>applicationContext.xml</b>配置文件
 *
 * @date 2012-10-12 下午4:23:13
 */
@Configuration
@EnableWebMvc
@ComponentScan({"my.ssh.biz.*.*.controller", "my.ssh.security"})
public class SpringMvcConfig extends WebMvcConfigurationSupport {

    private static final Logger logger = Logger.getLogger(SpringMvcConfig.class);

    @Resource
    private SecurityURLInterceptor securityURLInterceptor;

    @Bean
    public ContentNegotiationManager mvcContentNegotiationManager() {
        return super.mvcContentNegotiationManager();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).useJaf(false)
                .favorParameter(true).parameterName("mediaType")
                .ignoreAcceptHeader(true).
//                defaultContentType(MediaType.TEXT_PLAIN).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("json", MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML).
                mediaType("html",MediaType.TEXT_HTML).
                mediaType("txt",MediaType.TEXT_PLAIN);
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

//    /**
//     * 描述 : <HandlerMapping需要显示声明，否则不能注册资源访问处理器>. <br>
//     * <p>
//     * <这个比较奇怪,理论上应该是不需要的>
//     * </p>
//     *
//     * @return
//     */
//    @Bean
//    public HandlerMapping resourceHandlerMapping() {
//        logger.info("HandlerMapping");
//        return super.resourceHandlerMapping();
//    }
//
//    /**
//     * 描述 : <资源访问处理器>. <br>
//     * <p>
//     * <可以在jsp中使用/static/**的方式访问/WEB-INF/static/下的内容>
//     * </p>
//     *
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
//    }

    /**
     * 描述 : <RequestMappingHandlerMapping需要显示声明，否则不能注册自定义的拦截器>. <br>
     * <p>
     * <这个比较奇怪,理论上应该是不需要的>
     * </p>
     *
     * @return
     */
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return super.requestMappingHandlerMapping();
    }

    /**
     * 描述 : <添加拦截器>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //shiro请求拦截器
        registry.addInterceptor(securityURLInterceptor);
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();

        List<HttpMessageConverter<?>> list = getMessageConverters();
        for (HttpMessageConverter converter : list){
            if(converter instanceof StringHttpMessageConverter){
                StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
                list.remove(converter);
                list.add(stringHttpMessageConverter);
                break;
            }
        }

        return adapter;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        //指定所上传文件的总大小,单位字节。注意maxUploadSize属性的限制不是针对单个文件，而是所有文件的容量之和
        multipartResolver.setMaxUploadSize(1024*1024*1024);//1G
        return multipartResolver;
    }

}
