package my.ssh.security;

import my.ssh.biz.ssh.sys.entity.SysMenu;
import my.ssh.biz.ssh.sys.entity.SysRole;
import my.ssh.biz.ssh.sys.entity.SysUser;
import my.ssh.biz.ssh.sys.service.SysMenuService;
import my.ssh.biz.ssh.sys.service.SysUserService;
import my.ssh.util.UserDetailsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SecurityURLInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        response.setCharacterEncoding("UTF-8");

        String requestURI = request.getRequestURI();
        String contextURI = request.getContextPath() + "/";
        String method = request.getMethod(); //GET,PUT,POST,DELETE(分别对应查询，修改，新增，删除)

        requestURI = requestURI.substring(contextURI.length());

        if (requestURI.equals("login.jsp") || requestURI.equals("login")) {
            return true;
        }

        List<SysMenu> sysMenuList = sysMenuService.listAll();
        boolean hasUrl = false;
        for (SysMenu sysMenu : sysMenuList) {
            if(StringUtils.isNotBlank(sysMenu.getUrl()) && requestURI.startsWith(sysMenu.getUrl())){
                hasUrl = true;
                break;
            }
        }
        if(!hasUrl){
            return true;
        }

//        SysMenu _sysMenu = new SysMenu();
//        _sysMenu.setUrl(requestURI);
//        long requestUrlCount = sysMenuService.count(_sysMenu);
//        if (requestUrlCount == 0) {
//            return true;
//        }

        String username = UserDetailsUtils.getUsername();
        SysUser sysUser = new SysUser();
        // 判断是否登录
        if (null != username) {
            //判断session是否过期
            sysUser.setUsername(username);
            sysUser = sysUserService.getOne(sysUser);
        } else {
//            if (method.equals("GET")) {
//                return true;
//            }
            sysUser.setUsername("guest");
            sysUser = sysUserService.getOne(sysUser);
        }

        for (SysRole sysRole : sysUser.getSysRoles()) {
            for (SysMenu sysMenu : sysRole.getSysMenus()) {
                if (StringUtils.isNotBlank(sysMenu.getUrl()) && requestURI.startsWith(sysMenu.getUrl()) && method.equals(sysMenu.getMethod())) {
                    return true;
                }
            }
        }

        logger.warn("该用户(" + sysUser.getUsername() + ")没有访问该路径(" + requestURI + ")的权限！");
        response.getWriter().println("{success:false,msg:\"该用户没有权限访问该功能！\"}");

        return false;
    }
}
