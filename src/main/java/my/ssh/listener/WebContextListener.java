package my.ssh.listener;

import my.ssh.util.WebUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by admin on 2016/9/21.
 */
public class WebContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebUtils.setServletContext(sce.getServletContext());
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        WebUtils.setWebApplicationContext(ctx);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
