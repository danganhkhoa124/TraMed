package com.tramed.backend.infrastructure.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(UUID.class)
public class UuidTypeHandler extends BaseTypeHandler<UUID> {

  /**
   * Write UUID into database, no need 3rd type data
   *
   * @param ps PreparedStatement
   * @param i int
   * @param parameter UUID
   * @param jdbcType JdbcType
   * @throws SQLException when write UUID into database has error
   */
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setObject(i, parameter);
  }

  /**
   * Get UUID from database by column name
   *
   * @param rs ResultSet
   * @param columnName String
   * @return UUID type value
   * @throws SQLException when get UUID from database has error
   */
  @Override
  public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return rs.getObject(columnName, UUID.class);
  }

  /**
   * Get UUID from database by column index
   *
   * @param rs ResultSet
   * @param columnIndex int
   * @return UUID type value
   * @throws SQLException when get UUID from database has error
   */
  @Override
  public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return rs.getObject(columnIndex, UUID.class);
  }

  /**
   * Get UUID from database by column index with Callable Statement
   *
   * @param cs CallableStatement
   * @param columnIndex int
   * @return UUID type value
   * @throws SQLException when get UUID from database has error
   */
  @Override
  public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return cs.getObject(columnIndex, UUID.class);
  }
}
