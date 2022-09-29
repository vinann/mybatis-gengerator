<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperName}.${classInfo.className}Mapper">

    <resultMap id="BaseResultMap" type="${modelName}.${classInfo.className}" >
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                <result column="${fieldItem.columnName}" property="${fieldItem.fieldName}" />
            </#list>
        </#if>
    </resultMap>

    <sql id="Base_Column_List">
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
            </#list>
        </#if>
    </sql>

    <insert id="insert" parameterType="${modelName}.${classInfo.className}"  useGeneratedKeys="true" keyProperty="id">
        INSERT INTO ${schema}${classInfo.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                    <#if !fieldItem.isAutoIncrement && fieldItem.columnName !="create_at" && fieldItem.columnName !="update_at" >
                        <#--<#if fieldItem.fieldClass = "String" >
            ${r"<if test ='null != "}${fieldItem.fieldName} and "" != ${fieldItem.fieldName}${r"'>"}
                        <#else>-->
            ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}
                        <#--</#if>-->
                    ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
            ${r"</if>"}
                    </#if>
                </#list>
            </#if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                    <#if !fieldItem.isAutoIncrement && fieldItem.columnName !="create_at" && fieldItem.columnName !="update_at" >
                        <#--<#if fieldItem.fieldClass = "String" >
            ${r"<if test ='null != "}${fieldItem.fieldName} and "" != ${fieldItem.fieldName}${r"'>"}
                        <#else>-->
            ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}
                        <#--</#if>-->
                ${r"#{"}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>
            ${r"</if>"}
                    </#if>
                </#list>
            </#if>
        </trim>
    </insert>

    <insert id="insertList">
        INSERT INTO ${schema}${classInfo.tableName} (
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                <#if !fieldItem.isAutoIncrement && fieldItem.columnName !="create_at" && fieldItem.columnName !="update_at" >
            ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
                </#if>
            </#list>
        </#if>
        ) values
        <foreach collection="entities" item="i" separator=",">
            (
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                    <#if !fieldItem.isAutoIncrement && fieldItem.columnName !="create_at" && fieldItem.columnName !="update_at" >
                ${r"#{i."}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>
                    </#if>
                </#list>
            </#if>
            )
        </foreach>
    </insert>

    <delete id="delete" >
        DELETE FROM ${schema}${classInfo.tableName}
        WHERE
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                <#if fieldItem.isAutoIncrement>
            ${fieldItem.columnName}  = ${r"#{"}id${r"}"}
                </#if>
            </#list>
        </#if>
    </delete>

    <update id="update" parameterType="${modelName}.${classInfo.className}">
        UPDATE ${schema}${classInfo.tableName}
        <set>
            <#list classInfo.fieldList as fieldItem >
                <#if !fieldItem.isAutoIncrement && fieldItem.columnName != "create_at" && fieldItem.columnName != "update_at">
                    <#--<#if fieldItem.fieldClass = "String" >
            ${r"<if test ='null != "}${fieldItem.fieldName} and "" != ${fieldItem.fieldName}${r"'>"}${fieldItem.columnName} = ${r"#{"}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>${r"</if>"}
                    <#else>-->
            ${r"<if test ='null != "}${fieldItem.fieldName} ${r"'>"}${fieldItem.columnName} = ${r"#{"}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>${r"</if>"}
                    <#--</#if>-->
                </#if>
            </#list>
        </set>
        WHERE
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                <#if fieldItem.isAutoIncrement>
            ${fieldItem.columnName}  = ${r"#{"}id${r"}"}
                </#if>
            </#list>
        </#if>
    </update>


    <select id="findById" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM ${schema}${classInfo.tableName}
        WHERE
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                <#if fieldItem.isAutoIncrement>
            ${fieldItem.columnName}  = ${r"#{"}id${r"}"}
                </#if>
            </#list>
        </#if>
    </select>

    <select id="list" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM ${schema}${classInfo.tableName}
    </select>


</mapper>