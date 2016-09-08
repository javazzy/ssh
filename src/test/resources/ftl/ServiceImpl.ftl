${pojo.getPackageDeclaration()}

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.service.impl.*;
import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf(".service.impl"))}.dao.${pojo.getDeclarationName()}Dao;
import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf(".service.impl"))}.entity.${pojo.getDeclarationName()};
import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf("."))}.${pojo.getDeclarationName()}Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  服务实现： ${clazz.table.comment}
 */
@Service
public class ${pojo.getDeclarationName()}ServiceImpl extends <#if (pojo.getDeclarationName()?index_of('Dic') < 0)>Base</#if><#if (pojo.getDeclarationName()?index_of('Dic') == 0)>Cache</#if>ServiceImpl<${pojo.getDeclarationName()}> implements ${pojo.getDeclarationName()}Service{

    @Resource
    private ${pojo.getDeclarationName()}Dao ${pojo.getDeclarationName()?uncap_first}Dao;

    @Override
    public BaseDao<${pojo.getDeclarationName()}> getDao() {
        return ${pojo.getDeclarationName()?uncap_first}Dao;
    }
}
