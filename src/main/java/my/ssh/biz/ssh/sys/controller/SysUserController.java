package my.ssh.biz.ssh.sys.controller;

import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.common.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import my.ssh.biz.ssh.sys.entity.SysUser;
import my.ssh.biz.ssh.sys.service.SysUserService;

import java.util.List;

/**
 * 控制器：系统 - 用户表
 */
@Controller
@RequestMapping("/sysUsers")
public class SysUserController extends BaseController<SysUser> {

    @Resource
    private SysUserService sysUserService;

    @Override
    public BaseService getService() {
        return sysUserService;
    }
}
