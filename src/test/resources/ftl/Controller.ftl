${pojo.getPackageDeclaration()}

import my.ssh.biz.common.controller.*;
import my.ssh.biz.common.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf("."))}.entity.${pojo.getDeclarationName()};
import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf("."))}.service.${pojo.getDeclarationName()}Service;

/**
 * 控制器：${clazz.table.comment}
 */
@Controller
@RequestMapping("/${pojo.getDeclarationName()?uncap_first}s")
public class ${pojo.getDeclarationName()}Controller extends SimpleController<${pojo.getDeclarationName()}> {

    @Resource
    private ${pojo.getDeclarationName()}Service ${pojo.getDeclarationName()?uncap_first}Service;

    @Override
    public BaseService getService() {
        return ${pojo.getDeclarationName()?uncap_first}Service;
    }
}
