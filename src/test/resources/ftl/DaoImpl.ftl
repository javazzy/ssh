${pojo.getPackageDeclaration()}

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf("."))}.${pojo.getDeclarationName()}Dao;
import ${pojo.getPackageDeclaration().substring(8,pojo.getPackageDeclaration().lastIndexOf(".dao.impl"))}.entity.${pojo.getDeclarationName()};

import java.util.Calendar;

/**
 *  持久层实现： ${clazz.table.comment}
 */
@Repository
public class ${pojo.getDeclarationName()}DaoImpl extends BaseDaoImpl<${pojo.getDeclarationName()}> implements ${pojo.getDeclarationName()}Dao {

    public Class<${pojo.getDeclarationName()}> getClassType() {
        return ${pojo.getDeclarationName()}.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(${pojo.getDeclarationName()} entity, Page<${pojo.getDeclarationName()}> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
        <#foreach field in pojo.getAllPropertiesIterator()>
        <#if "int"=pojo.getJavaTypeName(field, jdk5)>
            <#foreach column in field.columnIterator><#if column.comment?exists && column.comment?trim?length!=0>
            /**
             * ${column.comment}
             */
            </#if></#foreach>
            if (entity.${pojo.getGetterSignature(field)}() > 0) {
               dc.add(Restrictions.eq("${field.name}", entity.${pojo.getGetterSignature(field)}()));
            }
        <#elseif  "Character"=pojo.getJavaTypeName(field, jdk5) || "Integer"=pojo.getJavaTypeName(field, jdk5)|| "Byte"=pojo.getJavaTypeName(field, jdk5)>
            <#foreach column in field.columnIterator><#if column.comment?exists && column.comment?trim?length!=0>
            /**
             * ${column.comment}
             */
            </#if></#foreach>
            if (null != entity.${pojo.getGetterSignature(field)}()) {
               dc.add(Restrictions.eq("${field.name}", entity.${pojo.getGetterSignature(field)}()));
            }
         <#elseif "String"=pojo.getJavaTypeName(field, jdk5)>
            <#foreach column in field.columnIterator><#if column.comment?exists && column.comment?trim?length!=0>
            /**
             * ${column.comment}
             */
            </#if></#foreach>
            if (StringUtils.isNotBlank(entity.${pojo.getGetterSignature(field)}())) {
                dc.add(Restrictions.like("${field.name}", entity.${pojo.getGetterSignature(field)}(), MatchMode.ANYWHERE));
            }
         <#elseif "Date"=pojo.getJavaTypeName(field, jdk5)>
            <#foreach column in field.columnIterator><#if column.comment?exists && column.comment?trim?length!=0>
            /**
             * ${column.comment}
             */
            </#if></#foreach>
            if (null != entity.${pojo.getGetterSignature(field)}()) {
                dc.add(Restrictions.between("${field.name}", entity.${pojo.getGetterSignature(field)}(), Calendar.getInstance().getTime()));
            }
        <#elseif !pojo.getJavaTypeName(field, jdk5).startsWith("Set")>
            <#foreach column in field.columnIterator><#if column.comment?exists && column.comment?trim?length!=0>
            /**
             * ${column.comment}
             */
            </#if></#foreach>
            if (null != entity.${pojo.getGetterSignature(field)}()) {
                dc.add(Restrictions.eq("${field.name}.${field.value.table.primaryKey.columns[0].name}", entity.${pojo.getGetterSignature(field)}().getId()));
            }
         </#if>
        </#foreach>
        }
        return dc;
    }
}