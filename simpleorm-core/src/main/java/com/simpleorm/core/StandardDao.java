package com.simpleorm.core;

import com.simpleorm.core.utils.JavaTypesToSqlTypes;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class StandardDao<T> extends Dao<T> {
  public StandardDao(Class clazz, Properties properties) {

    Entity entityAnnotation = (Entity) clazz.getAnnotation(Entity.class);

    if (entityAnnotation == null) {
      throw new RuntimeException(String.format("Class s% is not entity. Expected annotation @Entity", clazz.getCanonicalName()));
    }

    tableName = entityAnnotation.tableName() == null ? clazz.getCanonicalName().toUpperCase() : entityAnnotation.tableName();
    dbProperties = properties;
    entityClass = clazz;
    columnsList = new HashMap<>();
    StringBuffer fieldsListSB = new StringBuffer();
    StringBuffer fieldsWithTableNameListSB = new StringBuffer();
    int fieldNum = 1;
    Field[] fields = entityClass.getDeclaredFields();
    for (Field f : fields) {
      columnsList.put(f.getName(), f.getType().getSimpleName());
      fieldsListSB.append(f.getName());
      fieldsWithTableNameListSB.append(tableName).append(".").append(f.getName());

      if (fieldNum < fields.length) {
        fieldsListSB.append(", ");
        fieldsWithTableNameListSB.append(", ");
        fieldNum++;
      }
    }
    fieldsList = fieldsListSB.toString();
    fieldsWithTableNameList = fieldsWithTableNameListSB.toString();

  }

  @Override
  public T create(Connection conn, T entity) throws SQLException {

    String query = getInsertQuery();

    return null;
  }

  @Override
  public T create(T entity) throws SQLException {
    return create(connection, entity);
  }

  @Override
  public boolean create(Connection conn, Collection<T> entites) throws SQLException {
    return false;
  }

  @Override
  public boolean create(Collection<T> entites) throws SQLException {
    return false;
  }

  @Override
  public List<T> find(Connection conn, String where, Object... params) throws SQLException {
    return null;
  }

  @Override
  public List<T> find(String where, Object... params) throws SQLException {
    return null;
  }

  @Override
  public List<T> findAll(Connection conn) throws SQLException {
    return null;
  }

  @Override
  public List<T> findAll() throws SQLException {
    return null;
  }

  @Override
  public Map<String, T> findAllAsMap(Connection conn) throws SQLException {
    return null;
  }

  @Override
  public Map<String, T> findAllAsMap() throws SQLException {
    return null;
  }

  @Override
  public List<T> findById(Connection conn, Object id) throws SQLException {
    return null;
  }

  @Override
  public List<T> findById(Object id) throws SQLException {
    return null;
  }

  @Override
  public boolean update(Connection conn, T entity) throws SQLException {
    return false;
  }

  @Override
  public boolean update(T entity) throws SQLException {
    return false;
  }

  @Override
  public boolean delete(Connection conn, T entity) throws SQLException {
    return false;
  }

  @Override
  public boolean delete(T entity) throws SQLException {
    return false;
  }

  @Override
  public boolean delete(Connection conn, String where, Object... params) throws SQLException {
    return false;
  }

  @Override
  public boolean delete(String where, Object... params) throws SQLException {
    return false;
  }

  @Override
  public boolean deleteById(Connection conn, Object id) throws SQLException {
    return false;
  }

  @Override
  public boolean deleteById(Object id) throws SQLException {
    return false;
  }

  @Override
  public String getInsertQuery() {
    if (insertQuery != null && !insertQuery.isEmpty()) {
      return insertQuery;
    }

    StringBuffer query = new StringBuffer("INSERT INTO ");
    query.append(tableName).append("(").append(fieldsList).append(") values (");

    return query.toString();
  }

  @Override
  public String getSelectQuery() {
    return super.getSelectQuery();
  }

  @Override
  public String getUpdateQuery() {
    return super.getUpdateQuery();
  }

  @Override
  public String getDeleteQuery() {
    return super.getDeleteQuery();
  }
}
