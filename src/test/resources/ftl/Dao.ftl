${pojo.getPackageDeclaration()}

import my.ssh.biz.common.dao.BaseDao;
import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf("."))}.entity.${pojo.getDeclarationName()};

/**
 *  持久层接口： ${clazz.table.comment}
 */
public interface ${pojo.getDeclarationName()}Dao  extends BaseDao<${pojo.getDeclarationName()}> {
}
