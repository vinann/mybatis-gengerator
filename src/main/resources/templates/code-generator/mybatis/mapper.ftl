package ${packageName}.repository;

import ${packageName}.entity.${classInfo.className};
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

    int delete(String id);

    int update(${classInfo.className} ${classInfo.className?uncap_first});

    ${classInfo.className} findById(String id);

    List<${classInfo.className}> list();

    int insertList(@Param("entities") List<${classInfo.className}> entities);


}
