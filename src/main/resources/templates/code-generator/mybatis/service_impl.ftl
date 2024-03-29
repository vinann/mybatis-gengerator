package ${packageName}.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${packageName}.entity.${classInfo.className};
import ${packageName}.repository.${classInfo.className}Mapper;
import ${packageName}.service.${classInfo.className}Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* ${classInfo.classComment}
* @author ${authorName}
* @date ${.now?string('yyyy/MM/dd')}
*/
@Service
public class ${classInfo.className}ServiceImpl implements ${classInfo.className}Service {

	@Resource
	private ${classInfo.className}Mapper ${classInfo.className?uncap_first}Mapper;


	@Override
	public int insert(${classInfo.className} ${classInfo.className?uncap_first}) {
		return ${classInfo.className?uncap_first}Mapper.insert(${classInfo.className?uncap_first});
	}

	@Override
	public int delete(String id) {
		return ${classInfo.className?uncap_first}Mapper.delete(id);
	}

	@Override
	public int update(${classInfo.className} ${classInfo.className?uncap_first}) {
		return ${classInfo.className?uncap_first}Mapper.update(${classInfo.className?uncap_first});
	}

	@Override
	public ${classInfo.className} findById(String id) {
		return ${classInfo.className?uncap_first}Mapper.findById(id);
	}

	@Override
	public List<${classInfo.className}> list() {
		return ${classInfo.className?uncap_first}Mapper.list();
	}

	@Override
	public PageInfo<${classInfo.className}> listByPage(int page, int size) {
		PageHelper.startPage(page, size);
		return new PageInfo<>(${classInfo.className?uncap_first}Mapper.list());
	}

}
