package ${packageName}.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdeado.factory.dao.${className}Mapper;
import com.tdeado.factory.entity.${className};
import com.tdeado.factory.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public abstract class Base${className}ServiceImpl implements ${className}Service {
    @Autowired
    protected ${className}Mapper mapper;
    @Override
    public ${className} add${className}(${className} obj) {
        if (mapper.insert${className}(obj)>0){
            return obj;
        }
        throw new RuntimeException("${className}数据库添加失败");
    }
    @Override
    public ${className} edit${className}(${className} obj) {
        if (mapper.update${className}(obj)>0){
            return obj;
        }
        throw new RuntimeException("${className}数据库修改失败");
    }
    @Override
    public ${className} get${className}(${className} obj) {
        return mapper.query${className}Limit1(obj);
    }

    @Override
    public PageInfo<${className}> get${className}List(int page, int size, ${className} obj) {
        PageHelper.startPage(page, size);
        List<${className}> list = mapper.query${className}(obj);
        PageInfo<${className}> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    @Override
    public boolean del${className}(int id) {
        if (mapper.del${className}(id)>0){
            return true;
        }
        return false;
    }
}
