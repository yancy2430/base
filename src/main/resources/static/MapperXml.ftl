<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//ibatis.apache.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.dao.base.Base${className}Mapper">

    <#include "mysql/insertXML.ftl">

    <#include "mysql/updateXML.ftl">

    <#include "mysql/selectXML.ftl">

    <#include "mysql/deleteXML.ftl">

</mapper>