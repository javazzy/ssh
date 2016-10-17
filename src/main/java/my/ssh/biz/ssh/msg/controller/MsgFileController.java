package my.ssh.biz.ssh.msg.controller;

import java.util.List;
import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.common.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import my.ssh.biz.ssh.msg.entity.MsgFile;
import my.ssh.biz.ssh.msg.service.MsgFileService;

/**
 * 控制器：消息附件表
 */
@Controller
@RequestMapping("/msgFiles")
public class MsgFileController extends BaseController<MsgFile> {

    @Resource
    private MsgFileService msgFileService;

    @Override
    public BaseService getService() {
        return msgFileService;
    }

    /**
     * 批量新增
     * @param list
     * @return
     */
    @RequestMapping("/addAll")
    @ResponseBody
    @Override
    public Result addAll(@RequestBody List<MsgFile> list) {
        return super.addAll(list);
    }

}
