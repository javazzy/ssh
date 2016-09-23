package my.ssh.util;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zzy on 16/9/9.
 */
public class WebUtils {

    private static ServletContext servletContext;
    private static WebApplicationContext webApplicationContext;
    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal();
    private static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal();

    public static HttpServletRequest getRequest() {
        return requestThreadLocal.get();
    }

    public static HttpServletResponse getResponse() {
        return responseThreadLocal.get();
    }

    public static HttpSession getSession() {
        return requestThreadLocal.get().getSession();
    }


    public static void setRequest(HttpServletRequest request) {
        requestThreadLocal.set(request);
    }

    public static void setResponse(HttpServletResponse response) {
        responseThreadLocal.set(response);
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }
    public static void setServletContext(ServletContext servletContext) {
        WebUtils.servletContext = servletContext;
    }

    public static WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    public static void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        WebUtils.webApplicationContext = webApplicationContext;
    }
}
