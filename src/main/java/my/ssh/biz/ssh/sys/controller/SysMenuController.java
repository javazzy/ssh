package my.ssh.biz.ssh.sys.controller;

import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.common.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import my.ssh.biz.ssh.sys.entity.SysMenu;
import my.ssh.biz.ssh.sys.service.SysMenuService;

import java.util.List;

/**
 * 控制器：系统 - 菜单表
 */
@Controller
@RequestMapping("/sysMenus")
public class SysMenuController extends BaseController<SysMenu> {

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public BaseService getService() {
        return sysMenuService;
    }

}
