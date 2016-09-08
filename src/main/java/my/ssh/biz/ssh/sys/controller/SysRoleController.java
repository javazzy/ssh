package my.ssh.biz.ssh.sys.controller;

import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

import my.ssh.biz.ssh.sys.entity.SysRole;
import my.ssh.biz.ssh.sys.service.SysRoleService;

/**
 * 控制器：
 */
@Controller
@RequestMapping("/sysRoles")
public class SysRoleController extends SimpleController<SysRole> {

    @Resource
    private SysRoleService sysRoleService;

    @Override
    public BaseService getService() {
        return sysRoleService;
    }
}
