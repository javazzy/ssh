<#if ejb3>
<#if pojo.hasIdentifierProperty()>
<#if property.equals(clazz.identifierProperty)>
${pojo.generateAnnIdGenerator()}
    @${pojo.importType("javax.persistence.GeneratedValue")}(strategy = ${pojo.importType("javax.persistence.GenerationType")}.IDENTITY)</#if></#if><#if c2h.isOneToOne(property)>
    ${pojo.generateOneToOneAnnotation(property, md)}
<#elseif c2h.isManyToOne(property)>
    ${pojo.generateManyToOneAnnotation(property)}
${pojo.generateJoinColumnsAnnotation(property, md)}
<#elseif c2h.isCollection(property)>
    ${pojo.generateCollectionAnnotation(property, md)}
    @${pojo.importType("com.fasterxml.jackson.annotation.JsonIgnore")}
<#else>
${pojo.generateBasicAnnotation(property)}
<#--${pojo.generateAnnColumnAnnotation(property)}-->
    <#foreach column in property.columnIterator>
    @${pojo.importType("javax.persistence.Column")}(name="${column.quotedName}", columnDefinition= "<#if
    property.value.typeName='int'||property.value.typeName='java.lang.Integer'>int<#elseif
    property.value.typeName='string'||property.value.typeName='java.lang.String'><#if column.length=255>longtext<#else>varchar(${column.length})</#if><#elseif
    property.value.typeName='date'||property.value.typeName='java.lang.Date'>date<#elseif
    property.value.typeName='timestamp'>timestamp<#elseif
    property.value.typeName='chart'||property.value.typeName='java.lang.Character'>char<#elseif
    property.value.typeName='tinyint'||property.value.typeName='byte'||property.value.typeName='java.lang.Byte'>tinyint<#elseif
    property.value.typeName='boolean'||property.value.typeName='bool'||property.value.typeName='java.lang.Boolean'>boolean<#elseif
    property.value.typeName='double'||property.value.typeName='java.lang.Double'>double(${column.precision},${column.scale})<#elseif
    property.value.typeName='float'||property.value.typeName='java.lang.Float'>float(${column.precision},${column.scale})<#elseif
    property.value.typeName='binary'||property.value.typeName='blob'>blob</#if> comment '${column.comment}' <#if !column.naturalIdentifier&&column.nullable>null<#else>not null</#if>")
    </#foreach>
</#if>
</#if>