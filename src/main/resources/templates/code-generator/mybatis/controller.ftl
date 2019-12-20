package ${packageName}.controller;

import com.github.pagehelper.PageInfo;
import ${packageName}.entity.${classInfo.className};
import ${packageName}.service.${classInfo.className}Service;
import ${packageName}.utils.JsonResult;
import ${packageName}.utils.JsonResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* ${classInfo.classComment}
* @author ${authorName}
* @date ${.now?string('yyyy/MM/dd')}
*/
@RestController
@RequestMapping(value = "/${controllerName?uncap_first}")
@Api(tags={"${classInfo.classComment}"})
public class ${controllerName}Controller {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private JsonResultHelper jsonResultHelper;
    
    @Resource
    private ${classInfo.className}Service ${classInfo.className?uncap_first}Service;
    
    @PostMapping
    @ApiOperation(value = "新增")
    public JsonResult insert(@RequestBody @ApiParam("请求参数") ${classInfo.className} ${classInfo.className?uncap_first}){
        ${classInfo.className?uncap_first}Service.insert(${classInfo.className?uncap_first});
        return jsonResultHelper.buildSuccessJsonResult(null);
    }
    
    @DeleteMapping
    @ApiOperation(value = "刪除")
    public JsonResult delete(String id){
        ${classInfo.className?uncap_first}Service.delete(id);
        return jsonResultHelper.buildSuccessJsonResult(null);
    }
    
    @PutMapping
    @ApiOperation(value = "更新")
    public JsonResult update(@RequestBody @ApiParam("请求参数") ${classInfo.className} ${classInfo.className?uncap_first}){
        ${classInfo.className?uncap_first}Service.update(${classInfo.className?uncap_first});
        return jsonResultHelper.buildSuccessJsonResult(null);
    }
    
    @GetMapping
    @ApiOperation(value = "根据ID查询")
    public JsonResult<${classInfo.className}> findById(@ApiParam("ID") @RequestParam String id){
        return jsonResultHelper.buildSuccessJsonResult(${classInfo.className?uncap_first}Service.findById(id));
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询")
    public JsonResult<PageInfo<${classInfo.className}>> listByPage(
        @ApiParam("页码") @RequestParam int page,
        @ApiParam("页大小") @RequestParam int size) {
        return jsonResultHelper.buildSuccessJsonResult(${classInfo.className?uncap_first}Service.listByPage(page, size));
    }

}

