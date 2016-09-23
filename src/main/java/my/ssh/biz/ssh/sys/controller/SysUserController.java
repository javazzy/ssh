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

    /**
     * 批量新增
     * @param list
     * @return
     */
    @RequestMapping("/addAll")
    @ResponseBody
    @Override
    public Result addAll(@RequestBody List<SysUser> list) {
        return super.addAll(list);
    }

    /**
     * 批量删除
     * @param list
     * @return
     */
    @RequestMapping("/deleteAll")
    @ResponseBody
    @Override
    public Object deleteAll(@RequestBody List<SysUser> list) {
        return super.deleteAll(list);
    }

}
