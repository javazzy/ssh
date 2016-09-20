package my.ssh.biz.ssh.sys.controller;

import java.util.List;
import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.common.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import my.ssh.biz.ssh.sys.entity.SysLog;
import my.ssh.biz.ssh.sys.service.SysLogService;

/**
 * 控制器：ϵͳ - ��־��
 */
@Controller
@RequestMapping("/sysLogs")
public class SysLogController extends SimpleController<SysLog> {

    @Resource
    private SysLogService sysLogService;

    @Override
    public BaseService getService() {
        return sysLogService;
    }

    /**
     * 批量新增
     * @param list
     * @return
     */
    @RequestMapping("/addAll")
    @ResponseBody
    @Override
    public Result addAll(@RequestBody List<SysLog> list) {
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
    public Object deleteAll(@RequestBody List<SysLog> list) {
        return super.deleteAll(list);
    }

}
