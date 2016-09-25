package my.ssh.biz.ssh.dic.controller;

import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.common.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import my.ssh.biz.ssh.dic.entity.DicSex;
import my.ssh.biz.ssh.dic.service.DicSexService;

import java.util.List;

/**
 * 控制器：
 */
@Controller
@RequestMapping("/dicSexs")
public class DicSexController extends BaseController<DicSex> {

    @Resource
    private DicSexService dicSexService;

    @Override
    public BaseService getService() {
        return dicSexService;
    }

    /**
     * 批量新增
     * @param list
     * @return
     */
    @RequestMapping("/addAll")
    @ResponseBody
    @Override
    public Result addAll(@RequestBody List<DicSex> list) {
        return super.addAll(list);
    }

}
