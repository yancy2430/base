package ${packageName}.web.base;

import com.github.pagehelper.PageInfo;
import ${packageName}.entity.${className};
import ${packageName}.service.${className}Service;
import ${packageName}.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Base${className}Controller extends BaseController {

    @Autowired
    protected ${className}Service service;

    @RequestMapping(value = "${className}/add", method = {RequestMethod.POST})
    protected Map<String, Object> add(@RequestBody ${className} obj) {
        return retData(service.add${className}(obj));
    }

    @RequestMapping(value = "${className}/list", method = {RequestMethod.POST})
    protected Map<String, Object> list(@RequestBody ${className} obj, int page, int size) {
        PageInfo<${className}> list = service.get${className}List(page, size, obj);
    return retData(list);
    }

    @RequestMapping(value = "${className}/item", method = {RequestMethod.POST})
    protected Map<String, Object> item(@RequestBody ${className} obj) {
        return retData(service.get${className}(obj));
    }

    @RequestMapping(value = "${className}/dels", method = {RequestMethod.POST})
    protected Map<String, Object> dels(@RequestBody int[] obj) {
        int b = 0;
        for (int i : obj) {
            if (service.del${className}(i)){
                b++;
            }
        }
        return retData("成功"+b+"条,失败"+(obj.length-b));
    }
}
