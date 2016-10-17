package my.ssh.biz.ssh.msg.controller;

import java.util.List;
import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.common.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import my.ssh.biz.ssh.msg.entity.MsgRecord;
import my.ssh.biz.ssh.msg.service.MsgRecordService;

/**
 * 控制器：消息记录表
 */
@Controller
@RequestMapping("/msgRecords")
public class MsgRecordController extends BaseController<MsgRecord> {

    @Resource
    private MsgRecordService msgRecordService;

    @Override
    public BaseService getService() {
        return msgRecordService;
    }

    /**
     * 批量新增
     * @param list
     * @return
     */
    @RequestMapping("/addAll")
    @ResponseBody
    @Override
    public Result addAll(@RequestBody List<MsgRecord> list) {
        return super.addAll(list);
    }

}
