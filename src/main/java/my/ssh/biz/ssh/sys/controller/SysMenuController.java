package my.ssh.biz.ssh.sys.controller;

import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

import my.ssh.biz.ssh.sys.entity.SysMenu;
import my.ssh.biz.ssh.sys.service.SysMenuService;

/**
 * 控制器：
 */
@Controller
@RequestMapping("/sysMenus")
public class SysMenuController extends SimpleController<SysMenu> {

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public BaseService getService() {
        return sysMenuService;
    }
}
