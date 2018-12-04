package ${packageName}.dao.base;

import java.util.List;
import ${packageName}.entity.${className};

/**
*  @author author
*/
public interface Base${className}Mapper {

    int insert${className}(${className} object);

    int update${className}(${className} object);

    int update(${className}.UpdateBuilder object);

    List<${className}> query${className}(${className} object);

    ${className} query${className}Limit1(${className} object);

    int del${className}(int id);
}