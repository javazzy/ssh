${pojo.getPackageDeclaration()}

import java.util.List;
import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.common.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf("."))}.entity.${pojo.getDeclarationName()};
import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf("."))}.service.${pojo.getDeclarationName()}Service;

/**
 * 控制器：${clazz.table.comment}
 */
@Controller
@RequestMapping("/${pojo.getDeclarationName()?uncap_first}s")
public class ${pojo.getDeclarationName()}Controller extends BaseController<${pojo.getDeclarationName()}> {

    @Resource
    private ${pojo.getDeclarationName()}Service ${pojo.getDeclarationName()?uncap_first}Service;

    @Override
    public BaseService getService() {
        return ${pojo.getDeclarationName()?uncap_first}Service;
    }

    /**
     * 批量新增
     * @param list
     * @return
     */
    @RequestMapping("/addAll")
    @ResponseBody
    @Override
    public Result addAll(@RequestBody List<${pojo.getDeclarationName()}> list) {
        return super.addAll(list);
    }

}
