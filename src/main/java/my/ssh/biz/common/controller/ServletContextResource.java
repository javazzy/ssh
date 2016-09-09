package my.ssh.biz.common.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zzy on 16/9/9.
 */
public class ServletContextResource {


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

    public static javax.servlet.ServletContext getServletContext() {
        return requestThreadLocal.get().getServletContext();
    }

    public static void setRequest(HttpServletRequest request) {
        requestThreadLocal.set(request);
    }

    public static void setResponse(HttpServletResponse response) {
        responseThreadLocal.set(response);
    }

}
