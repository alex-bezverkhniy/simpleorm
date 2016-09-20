package com.simpleorm.core.utils;

public enum JavaTypesToSqlTypes {
    CHAR("java.lang.String"),
    VARCHAR	("java.lang.String"),
    LONGVARCHAR("java.lang.String"),
    NUMERIC("java.math.BigDecimal"),
    DECIMAL("java.math.BigDecimal"),
    BIT("boolean"),
    TINYINT("byte"),
    SMALLINT("short"),
    INTEGER("int"),
    BIGINT("long"),
    REAL("float"),
    FLOAT("double"),
    DOUBLE("double"),
    BINARY("byte[]"),
    VARBINARY("byte[]"),
    LONGVARBINARY("byte[]"),
    DATE("java.sql.Date"),
    TIME("java.sql.Time"),
    TIMESTAMP("java.sql.Timestamp"),
    CLOB("java.sql.Clob"),
    BLOB("java.sql.Blob"),
    ARRAY("java.sql.Array"),
    DISTINCT("Mapping of underlying type"),
    STRUCT("java.sql.Struct"),
    REF("java.sql.Ref");

    private String className;

    JavaTypesToSqlTypes(String className) {
        this.className = className;
    }
}
