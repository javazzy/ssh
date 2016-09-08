package my.ssh.biz.ssh.sys.controller;

import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

import my.ssh.biz.ssh.sys.entity.SysUser;
import my.ssh.biz.ssh.sys.service.SysUserService;

/**
 * 控制器：
 */
@Controller
@RequestMapping("/sysUsers")
public class SysUserController extends SimpleController<SysUser> {

    @Resource
    private SysUserService sysUserService;

    @Override
    public BaseService getService() {
        return sysUserService;
    }
}
