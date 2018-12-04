
    <delete id="del${className}" parameterType="int">
        delete from ${tableName} where id=${"#\{"}id}
    </delete>