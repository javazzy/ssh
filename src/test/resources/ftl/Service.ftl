${pojo.getPackageDeclaration()}

import my.ssh.biz.common.service.BaseService;
import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf("."))}.entity.${pojo.getDeclarationName()};

/**
 *  服务接口： ${clazz.table.comment}
 */
public interface ${pojo.getDeclarationName()}Service extends BaseService<${pojo.getDeclarationName()}> {

}
