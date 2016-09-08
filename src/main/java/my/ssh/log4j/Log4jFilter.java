package my.ssh.log4j;

import my.ssh.biz.ssh.sys.entity.SysUser;
import org.apache.log4j.MDC;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 争洋 on 2015/5/19 0019.
 */
public class Log4jFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (null == session) {
            MDC.put("USERID", "");
            MDC.put("USERNAME", "");
        } else {
            SysUser user = (SysUser) session.getAttribute("user");
            if (null == user) {
                MDC.put("USERID", "");
                MDC.put("USERNAME", "");
            } else {
                MDC.put("USERID", user.getId());
                MDC.put("USERNAME", user.getUsername());
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
