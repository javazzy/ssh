package my.ssh.biz.ssh.sys.controller;

import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.common.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import my.ssh.biz.ssh.sys.entity.SysRole;
import my.ssh.biz.ssh.sys.service.SysRoleService;

import java.util.List;

/**
 * 控制器：系统 - 角色表
 */
@Controller
@RequestMapping("/sysRoles")
public class SysRoleController extends BaseController<SysRole> {

    @Resource
    private SysRoleService sysRoleService;

    @Override
    public BaseService getService() {
        return sysRoleService;
    }
}
