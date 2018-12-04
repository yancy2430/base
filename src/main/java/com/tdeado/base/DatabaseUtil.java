package com.tdeado.base;

import com.tdeado.base.generating.MySqlData;
import com.tdeado.base.generating.TableData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://mysql.tdeado.com:3306/base?useUnicode=true&characterEncoding=utf8&useOldAliasMetadataBehavior=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "asd243046870";

    private static final String SQL = "SELECT * FROM ";// 数据库操作

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public static List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            LOGGER.error("getTableNames failure", e);
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                LOGGER.error("close ResultSet failure", e);
            }
        }
        return tableNames;
    }


    public static void main(String[] args) {

        List<MySqlData> ss = getTableColumns("tb_access");
        System.err.println(ss.toString());
    }

    public static TableData getTableData(String tableName) {
        TableData tableData = new TableData();
        tableData.setTableName(tableName);
        tableData.setColumns(getTableColumns(tableName));
        Optional<MySqlData> optionalMySqlData = tableData.getColumns().stream().filter(m -> !m.getIsAuto().equals("NO")).findFirst();
        optionalMySqlData.ifPresent(mySqlData -> tableData.setAutoKey(mySqlData.getPropertiesName()));
        return tableData;
    }
    public static List<MySqlData> getTableColumns(String tableName) {
        List<MySqlData> mySqlDataList = new ArrayList<MySqlData>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            Set<String> keySet = new HashSet<>();
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            rs = databaseMetaData.getPrimaryKeys(null, "base", tableName);
            while (rs.next()) {
                keySet.add(rs.getString("COLUMN_NAME"));
            }
            rs = databaseMetaData.getColumns(null,"base", tableName, "%");

            while (rs.next()) {
                MySqlData mySqlData = new MySqlData();
                mySqlData.setColumnName(rs.getString("COLUMN_NAME"));
                mySqlData.setPropertiesName(underLineToCamel(mySqlData.getColumnName(), true));
                mySqlData.setTypeId(rs.getInt("DATA_TYPE"));
                mySqlData.setTypeName(rs.getString("TYPE_NAME"));
                mySqlData.setJavaTypeName(transfer(mySqlData.getTypeName()));
                mySqlData.setIsBetween(isBetween(mySqlData.getTypeName()));
                mySqlData.setRemarks(rs.getString("REMARKS"));
                mySqlData.setNullAble(rs.getInt("NULLABLE"));
                mySqlData.setColumnDef(rs.getString("COLUMN_DEF"));
                mySqlData.setIsAuto(rs.getString("IS_AUTOINCREMENT"));
                if (keySet.contains(mySqlData.getColumnName())) {
                    mySqlData.setIsKey(1);
                } else {
                    mySqlData.setIsKey(0);
                }
                mySqlDataList.add(mySqlData);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }

        return mySqlDataList;
    }

    public static void saveFile(String string) {
        try {
            // 准备文件666.txt其中的内容是空的
            File f1 = new File("/Users/yangzhe/tbinfo.json");
            if (f1.exists() == false) {
                f1.getParentFile().mkdirs();
            }
            // 创建基于文件的输出流
            FileOutputStream fos = new FileOutputStream(f1);
            // 把数据写入到输出流
            fos.write(string.getBytes());
            // 关闭输出流
            fos.close();
            System.out.println("输入完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class DBTb {
        private String tbName;
        private MySqlData tbInfo;

        public DBTb(String tbName, MySqlData tbInfo) {
            this.tbName = tbName;
        }

        public String getTbName() {
            return tbName;
        }

        public void setTbName(String tbName) {
            this.tbName = tbName;
        }

        public MySqlData getTbInfo() {
            return tbInfo;
        }

        public void setTbInfo(MySqlData tbInfo) {
            this.tbInfo = tbInfo;
        }
    }


    public static String transfer(String typeName) {
        typeName = typeName.replaceAll(" UNSIGNED", "");
        switch (typeName) {
            case "VARCHAR":
                return "String";
            case "CHAR":
                return "String";
            case "TEXT":
                return "String";
            case "INTEGER":
                return "Long";
            case "BIGINT":
                return "Long";
            case "TINYINT":
                return "Integer";
            case "SMALLINT":
                return "Integer";
            case "MEDIUMINT":
                return "Integer";
            case "INT":
                return "Integer";
            case "FLOAT":
                return "Float";
            case "DOUBLE":
                return "Double";
            case "DECIMAL":
                return "java.math.BigDecimal";
            case "DATE":
                return "java.time.LocalDate";
            case "TIME":
                return "java.time.LocalTime";
            case "TIMESTAMP":
                return "java.time.LocalDateTime";
            case "MEDIUMTEXT":
                return "String";
            case "LONGTEXT":
                return "String";
            case "DATETIME":
                return "java.time.LocalDateTime";
            case "BIT":
                return "Integer";
            default:
                throw new IllegalArgumentException(typeName + " no such typeName,please edit db.mysql.process.TypeSwitch");
        }
    }
    public static String isBetween(String typeName) {
        typeName = typeName.replaceAll(" UNSIGNED", "").toUpperCase();
        switch (typeName) {
            case "VARCHAR":
                return "no";
            case "CHAR":
                return "no";
            case "TEXT":
                return "no";
            case "INTEGER":
                return "yes";
            case "BIGINT":
                return "yes";
            case "TINYINT":
                return "yes";
            case "SMALLINT":
                return "yes";
            case "MEDIUMINT":
                return "yes";
            case "INT":
                return "yes";
            case "FLOAT":
                return "yes";
            case "DOUBLE":
                return "yes";
            case "DECIMAL":
                return "yes";
            case "DATE":
                return "yes";
            case "TIME":
                return "yes";
            case "TIMESTAMP":
                return "yes";
            case "MEDIUMTEXT":
                return "no";
            case "LONGTEXT":
                return "no";
            case "DATETIME":
                return "yes";
            case "BIT":
                return "yes";
            case "INT8":
                return "yes";
            case "INT4":
                return "yes";
            case "INT2":
                return "yes";
            case "BOOL":
                return "no";
            case "FLOAT4":
                return "yes";
            case "FLOAT8":
                return "yes";
            case "BIGSERIAL":
                return "yes";
            case "SERIAL":
                return "yes";
            default:
                throw new IllegalArgumentException(typeName + " no such typeName,please edit db.mysql.process.TypeSwitch");
        }
    }


    /**
     * 下划线转驼峰
     *
     * @param content
     * @param firstUpperCase
     * @return
     */
    public static String underLineToCamel(String content, boolean firstUpperCase) {
        if (content == null || content.length() == 0) {
            return "";
        }

        if (!isNeedChange(content)) {
            return content;
        }

        String result = Stream.of(content.split("_")).map(m -> {
            String text = m;
            text = text.substring(0, 1).toUpperCase() + text.substring(1);
            return text;
        }).collect(Collectors.joining());
        if (firstUpperCase) {
            return result.substring(0, 1).toLowerCase() + result.substring(1);
        } else {
            return result;
        }
    }

    private static boolean isNeedChange(String content) {
        return content.contains("_");
    }
}