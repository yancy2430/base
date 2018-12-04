package ${packageName}.service.base;

import ${packageName}.entity.${className};
import com.github.pagehelper.PageInfo;

public interface Base${className}Service {
    ${className} add${className}(${className} obj);
    ${className} edit${className}(${className} obj);
    PageInfo<${className}> get${className}List(int page, int size, ${className} obj);
    ${className} get${className}(${className} obj);
    boolean del${className}(int id);
}
