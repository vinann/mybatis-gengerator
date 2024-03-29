package com.softdev.system.generator.controller;

import com.softdev.system.generator.entity.ClassInfo;
import com.softdev.system.generator.entity.ReturnT;
import com.softdev.system.generator.util.CodeGeneratorTool;
import com.softdev.system.generator.util.FileUtil;
import com.softdev.system.generator.util.FreemarkerTool;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * spring boot code generator
 * @author zhengk/moshow
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private FreemarkerTool freemarkerTool;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    public static void main(String[] args) {
        String content = " bcb.ID,\n" +
                "\tbcb.cost_id,\n" +
                "\tbcb.product_id,\n" +
                "\tP.NAME AS producrName,\n" +
                "\tbcb.price,\n" +
                "\tbcb.fare,\n" +
                "\tbcb.from_site_id,\n" +
                "\t( SELECT s.NAME FROM cortp.site s WHERE s.ID = bcb.from_site_id ) AS fromSiteId,\n" +
                "\tbcb.to_site_id,\n" +
                "\t( SELECT s.NAME FROM cortp.site s WHERE s.ID = bcb.to_site_id ) AS toSiteId,\n" +
                "\tbcb.service_fee,\n" +
                "\tbcb.maintenance_fee,\n" +
                "\tbcb.TYPE,\n" +
                "\tbcb.create_time,\n" +
                "\tbcb.num,\n" +
                "\tbcb.amount,\n" +
                "\tbcb.order_product_id,\n" +
                "\tbcb.contract_code,\n" +
                "\tbcb.settlement_date ";
        content = content.toLowerCase();

        String pre = "\n" +
                "    @ApiModelProperty(value = \"\")\n" +
                "    private String ";
        String[] ss = content.split(",");
        for (String str : ss) {
            String s = str;
            s = str.substring(s.indexOf(".")+1);
            int i =s.indexOf("AS");
            s = i>=0?s.substring(i):s;
            s = s.trim().replace("\"","");
            s = com.softdev.system.generator.util.StringUtils.underlineToCamelCase(s);
            //System.out.println(pre+s+";");
            System.out.println(""+str.trim()+" AS "+s+",");
        }
    }

    @RequestMapping("/gen")
    @ResponseBody
    public String gen(){
        try {
            FileReader fileReader = new FileReader("C:/Users/horen/Desktop/bc.sql");
            BufferedReader reader = new BufferedReader(fileReader);
            int size=0;
            String sql = "";
            String line;
            while((line=reader.readLine())!=null)
            {
                sql+=line;
            }
            reader.close();

            for (String tableSql : sql.split("CREATE TABLE")) {

                try {
                    if(tableSql.contains("DROP") || tableSql.equals("")){
                        continue;
                    }
                    tableSql = "CREATE TABLE"+tableSql;
                    // parse table
                    ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql, true);

                    // code genarete
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("classInfo", classInfo);
                    params.put("authorName", "wn");
                    String packageName = "com.horen.cortp";
                    params.put("packageName", packageName);
                    params.put("returnUtil", "");
                    String pre = classInfo.getTableName().split("_")[0];
                    pre = com.softdev.system.generator.util.StringUtils.upperCaseFirst(pre);
                    String controllerName = classInfo.getClassName().replace(pre,"");
                    params.put("controllerName", controllerName);
                    params.put("schema", "bc.");


                    // result
                    Map<String, String> result = new HashMap<String, String>();


                    //mybatis old
                    String controller = freemarkerTool.processString("code-generator/mybatis/controller.ftl", params);
                    result.put("controller", controller);
                    String service = freemarkerTool.processString("code-generator/mybatis/service.ftl", params);
                    result.put("service", service);
                    String service_impl = freemarkerTool.processString("code-generator/mybatis/service_impl.ftl", params);
                    result.put("service_impl", service_impl);
                    String mapper = freemarkerTool.processString("code-generator/mybatis/mapper.ftl", params);
                    result.put("mapper", mapper);
                    String mybatis = freemarkerTool.processString("code-generator/mybatis/mybatis.ftl", params);
                    result.put("mybatis", mybatis);
                    String model = freemarkerTool.processString("code-generator/mybatis/model.ftl", params);
                    result.put("model", model);

                    FileUtil.saveJavaFile(controller, "D:/code", packageName+".controller", controllerName + "Controller.java");
                    FileUtil.saveJavaFile(service, "D:/code", packageName+".service", classInfo.getClassName() + "Service.java");
                    FileUtil.saveJavaFile(service_impl, "D:/code", packageName+".service.impl", classInfo.getClassName() + "ServiceImpl.java");
                    FileUtil.saveJavaFile(mapper, "D:/code", packageName+".repository", classInfo.getClassName() + "Mapper.java");
                    FileUtil.saveJavaFile(mybatis, "D:/code", packageName+".mapping", classInfo.getClassName() + "Mapper.xml");
                    FileUtil.saveJavaFile(model, "D:/code", packageName+".entity", classInfo.getClassName() + ".java");
                }  catch (Exception e) {
                    e.printStackTrace();
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ok";
    }

    @RequestMapping("/genCode")
    @ResponseBody
    public ReturnT<Map<String, String>> codeGenerate(String tableSql,
                                                     //2019-2-10 liutf 修改为@RequestParam参数校验
                                                     @RequestParam(required = false, defaultValue = "大狼狗") String authorName,
                                                     @RequestParam(required = false, defaultValue = "com.softdev.system")String packageName,
                                                     @RequestParam(required = false, defaultValue = "ApiReturnUtil")String returnUtil,
                                                     @RequestParam(required = false, defaultValue = "true")boolean isUnderLineToCamelCase
    ) {


        try {
            if (StringUtils.isBlank(tableSql)) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "表结构信息不可为空");
            }


            for (String sql : tableSql.split("CREATE TABLE")) {

            }


            // parse table
            ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql, isUnderLineToCamelCase);

            // code genarete
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("classInfo", classInfo);
            params.put("authorName", authorName);
            params.put("packageName", packageName);
            params.put("returnUtil", returnUtil);
            String pre = classInfo.getTableName().split("_")[0];
            pre = com.softdev.system.generator.util.StringUtils.upperCaseFirst(pre);
            String controllerName = classInfo.getClassName().replace(pre,"");
            params.put("controllerName", controllerName);
            params.put("schema", "bc.");


            // result
            Map<String, String> result = new HashMap<String, String>();

            //UI
            result.put("swagger-ui", freemarkerTool.processString("code-generator/ui/swagger-ui.ftl", params));
            result.put("element-ui", freemarkerTool.processString("code-generator/ui/element-ui.ftl", params));
            result.put("bootstrap-ui", freemarkerTool.processString("code-generator/ui/bootstrap-ui.ftl", params));









            //mybatis old
            String controller = freemarkerTool.processString("code-generator/mybatis/controller.ftl", params);
            result.put("controller", controller);
            String service = freemarkerTool.processString("code-generator/mybatis/service.ftl", params);
            result.put("service", service);
            String service_impl = freemarkerTool.processString("code-generator/mybatis/service_impl.ftl", params);
            result.put("service_impl", service_impl);
            String mapper = freemarkerTool.processString("code-generator/mybatis/mapper.ftl", params);
            result.put("mapper", mapper);
            String mybatis = freemarkerTool.processString("code-generator/mybatis/mybatis.ftl", params);
            result.put("mybatis", mybatis);
            String model = freemarkerTool.processString("code-generator/mybatis/model.ftl", params);
            result.put("model", model);

            FileUtil.saveJavaFile(controller, "D:/code", packageName+".controller", controllerName + "Controller.java");
            FileUtil.saveJavaFile(service, "D:/code", packageName+".service", classInfo.getClassName() + "Service.java");
            FileUtil.saveJavaFile(service_impl, "D:/code", packageName+".service.impl", classInfo.getClassName() + "ServiceImpl.java");
            FileUtil.saveJavaFile(mapper, "D:/code", packageName+".repository", classInfo.getClassName() + "Mapper.java");
            FileUtil.saveJavaFile(mybatis, "D:/code", packageName+".mapping", classInfo.getClassName() + "Mapper.xml");
            FileUtil.saveJavaFile(model, "D:/code", packageName+".entity", classInfo.getClassName() + ".java");



















            //jpa
            result.put("entity", freemarkerTool.processString("code-generator/jpa/entity.ftl", params));
            result.put("repository", freemarkerTool.processString("code-generator/jpa/repository.ftl", params));
            result.put("jpacontroller", freemarkerTool.processString("code-generator/jpa/jpacontroller.ftl", params));
            //jdbc template
            result.put("jtdao", freemarkerTool.processString("code-generator/jdbc-template/jtdao.ftl", params));
            result.put("jtdaoimpl", freemarkerTool.processString("code-generator/jdbc-template/jtdaoimpl.ftl", params));
            //beetsql
            result.put("beetlmd", freemarkerTool.processString("code-generator/beetlsql/beetlmd.ftl", params));
            result.put("beetlentity", freemarkerTool.processString("code-generator/beetlsql/beetlentity.ftl", params));
            result.put("beetlentitydto", freemarkerTool.processString("code-generator/beetlsql/beetlentitydto.ftl", params));
            result.put("beetlcontroller", freemarkerTool.processString("code-generator/beetlsql/beetlcontroller.ftl", params));
            //mybatis plus
            result.put("pluscontroller", freemarkerTool.processString("code-generator/mybatis-plus/pluscontroller.ftl", params));
            result.put("plusmapper", freemarkerTool.processString("code-generator/mybatis-plus/plusmapper.ftl", params));
            //util
            result.put("util", freemarkerTool.processString("code-generator/util/util.ftl", params));
            //sql generate
            result.put("select", freemarkerTool.processString("code-generator/sql/select.ftl", params));
            result.put("insert", freemarkerTool.processString("code-generator/sql/insert.ftl", params));
            result.put("update", freemarkerTool.processString("code-generator/sql/update.ftl", params));
            result.put("delete", freemarkerTool.processString("code-generator/sql/delete.ftl", params));

            // 计算,生成代码行数
            int lineNum = 0;
            for (Map.Entry<String, String> item: result.entrySet()) {
                if (item.getValue() != null) {
                    lineNum += StringUtils.countMatches(item.getValue(), "\n");
                }
            }
            //log.info("生成代码行数：{}", lineNum);
            //测试环境可自行开启
            //log.info("生成代码数据：{}", result);
            return new ReturnT<>(result);
        } catch (IOException | TemplateException e) {
            //log.error(e.getMessage(), e);
            return new ReturnT<>(ReturnT.FAIL_CODE, "表结构解析失败"+e.getMessage());
        }

    }

}
