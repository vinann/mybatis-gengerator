package ${serviceName};

<#--import com.github.pagehelper.PageInfo;-->
import ${modelName}.${classInfo.className};

import java.util.List;

/**
* ${classInfo.classComment}Service接口
* @author ${authorName}
* @date ${.now?string('yyyy/MM/dd')}
*/
public interface ${classInfo.className}Service {

    int insert(${classInfo.className} ${classInfo.className?uncap_first});

    int delete(Integer id);

    int update(${classInfo.className} ${classInfo.className?uncap_first});

    ${classInfo.className} findById(Integer id);

    List<${classInfo.className}> list();

<#--    PageInfo<${classInfo.className}> listByPage(int page, int size);-->

}
