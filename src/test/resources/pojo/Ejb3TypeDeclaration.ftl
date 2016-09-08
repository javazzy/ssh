<#if ejb3?if_exists>
<#if pojo.isComponent()>
@${pojo.importType("javax.persistence.Embeddable")}
<#else>
@${pojo.importType("javax.persistence.Entity")}
@${pojo.importType("javax.persistence.Table")}(name="${clazz.table.name}"<#if clazz.table.schema?exists>,schema="${clazz.table.schema}"</#if><#if clazz.table.catalog?exists>,catalog="${clazz.table.catalog}"</#if><#assign uniqueConstraint=pojo.generateAnnTableUniqueConstraint()><#if uniqueConstraint?has_content>, uniqueConstraints = ${uniqueConstraint}</#if>)
</#if>
</#if>
@${pojo.importType("org.hibernate.annotations.Table")}(appliesTo = "${clazz.table.name}",comment = "${clazz.table.comment}")
@${pojo.importType("com.fasterxml.jackson.annotation.JsonInclude")}(${pojo.importType("com.fasterxml.jackson.annotation.JsonInclude")}.Include.NON_NULL)
