package ${mapperName};

import ${modelName}.${classInfo.className};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
* ${classInfo.classComment}Mapper
* @author ${authorName}
* @date ${.now?string('yyyy/MM/dd')}
*/
@Mapper
@Repository
public interface ${classInfo.className}Mapper {

    int insert(${classInfo.className} ${classInfo.className?uncap_first});

    int delete(Integer id);

    int update(${classInfo.className} ${classInfo.className?uncap_first});

    ${classInfo.className} findById(Integer id);

    List<${classInfo.className}> list();

    int insertList(@Param("entities") List<${classInfo.className}> entities);


}
