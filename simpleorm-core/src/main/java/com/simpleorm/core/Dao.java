package com.simpleorm.core;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public abstract class Dao<T> {
  public static final String DB_PROPERTIES_FILENAME = "simpleorm-db.properties";

  protected static Properties dbProperties;
  protected Connection connection;
  protected Class entityClass;

  protected String tableName;
  protected String insertQuery;
  protected String selectQuery;
  protected String updateQuery;
  protected String deleteQuery;

  protected String fieldsList;
  protected String fieldsWithTableNameList;

  private static Map<String, Dao> daoCache = new HashMap<>();

  private static final Logger log = LoggerFactory.getLogger(Dao.class);

  public static synchronized <ST> Dao<ST> getInstance(Class clazz, Properties properties) {
    if (clazz == null) {
      throw new IllegalArgumentException("Entity class shouldn't be null");
    }
    if (properties == null) {
      try {
        properties = loadDefaultDbProperties();
      } catch (IOException e) {
        log.error(String.format("Properties file %s is not found", DB_PROPERTIES_FILENAME), e);
      }
    }

    Dao<ST> result = daoCache.get(clazz.getName());

    if (result == null) {
      result = new StandardDao<ST>(clazz, properties);
      daoCache.put(clazz.getName(), result);
    }

    return result;
  }

  public static synchronized <S> Dao<S> getInstance(Class clazz) {
    return getInstance(clazz, null);
  }

  public abstract T create(Connection conn, T entity) throws SQLException;

  public abstract T create(T entity) throws SQLException;

  public abstract boolean create(Connection conn, Collection<T> entites) throws SQLException;

  public abstract boolean create(Collection<T> entites) throws SQLException;

  public abstract List<T> find(Connection conn, String where, Object... params) throws SQLException;

  public abstract List<T> find(String where, Object... params) throws SQLException;

  public abstract List<T> findAll(Connection conn) throws SQLException;

  public abstract List<T> findAll() throws SQLException;

  public abstract Map<String, T> findAllAsMap(Connection conn) throws SQLException;

  public abstract Map<String, T> findAllAsMap() throws SQLException;

  public abstract List<T> findById(Connection conn, Object id) throws SQLException;

  public abstract List<T> findById(Object id) throws SQLException;

  public abstract boolean update(Connection conn, T entity) throws SQLException;

  public abstract boolean update(T entity) throws SQLException;

  public abstract boolean delete(Connection conn, T entity) throws SQLException;

  public abstract boolean delete(T entity) throws SQLException;

  public abstract boolean delete(Connection conn, String where, Object... params) throws SQLException;

  public abstract boolean delete(String where, Object... params) throws SQLException;

  public abstract boolean deleteById(Connection conn, Object id) throws SQLException;

  public abstract boolean deleteById(Object id) throws SQLException;

  protected static synchronized Properties loadDefaultDbProperties() throws IOException {
    dbProperties = new Properties();
    dbProperties.load(Dao.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_FILENAME));
    return dbProperties;
  }

  public Properties getDbProperties() {
    return dbProperties;
  }

  public void setDbProperties(Properties dbProperties) {
    this.dbProperties = dbProperties;
  }

  public Connection getConnection() throws SQLException {
    DataSource dataSource = new DataSource();
    /*
    simpleorm.db.drivername=org.h2.Driver
simpleorm.db.username=sa
simpleorm.db.password=
simpleorm.db.url=jdbc:h2:mem:simpleorm_test_mem
simpleorm.db.maxactive=64
simpleorm.db.maxidle=8
simpleorm.db.validationwuery=SELECT 1
    */
    dataSource.setDriverClassName(dbProperties.getProperty("simpleorm.db.drivername"));
    dataSource.setUrl(dbProperties.getProperty("simpleorm.db.url"));
    dataSource.setUsername(dbProperties.getProperty("simpleorm.db.username"));
    dataSource.setUsername(dbProperties.getProperty("simpleorm.db.password"));
    dataSource.setMaxActive(Integer.parseInt(dbProperties.getProperty("simpleorm.db.maxactive")));
    dataSource.setMaxIdle(Integer.parseInt(dbProperties.getProperty("simpleorm.db.maxidle")));
    dataSource.setValidationQuery(dbProperties.getProperty("simpleorm.db.validationwuery"));
    connection = dataSource.getConnection();

    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  public String getInsertQuery() {
    return insertQuery;
  }

  public void setInsertQuery(String insertQuery) {
    this.insertQuery = insertQuery;
  }

  public String getSelectQuery() {
    return selectQuery;
  }

  public void setSelectQuery(String selectQuery) {
    this.selectQuery = selectQuery;
  }

  public String getUpdateQuery() {
    return updateQuery;
  }

  public void setUpdateQuery(String updateQuery) {
    this.updateQuery = updateQuery;
  }

  public String getDeleteQuery() {
    return deleteQuery;
  }

  public void setDeleteQuery(String deleteQuery) {
    this.deleteQuery = deleteQuery;
  }
}
