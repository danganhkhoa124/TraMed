package com.tramed.backend.infrastructure.mybatis.typehandler;

import com.tramed.backend.core.base.model.common.EnumDBColumn;
import jakarta.annotation.Nullable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * EnumValueTypeHandler that follow Java naming convention. This stores the lower case to database.
 *
 * @param <E> enum class
 */
@MappedTypes(EnumDBColumn.class)
public class EnumValueJavaConventionTypeHandler<E extends Enum<E> & EnumDBColumn>
    extends BaseTypeHandler<E> {

  private final Class<E> type;

  /**
   * Constructor
   *
   * @param type Enum Class
   * @throws NullPointerException when Enum Class was null
   */
  public EnumValueJavaConventionTypeHandler(Class<E> type) {
    if (type == null) {
      throw new NullPointerException("Enum class cannot be null.");
    }
    this.type = type;
  }

  /**
   * When create or update call this func for get value to update in database
   *
   * @param ps PreparedStatement
   * @param i int
   * @param parameter E
   * @param jdbcType JdbcType
   * @throws SQLException when this func return error
   */
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, parameter.name());
  }

  /**
   * When get value call this func for return enum
   *
   * @param rs ResultSet
   * @param columnName Column name, when configuration <code>useColumnLabel</code> is <code>false
   *     </code>
   * @return the enum
   * @throws SQLException the SQL exception
   */
  @Override
  public @Nullable E getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String value = rs.getString(columnName);
    if (rs.wasNull()) {
      return null;
    }
    return convertToEnum(value);
  }

  /**
   * When get value call this func for return enum
   *
   * @param rs ResultSet
   * @param columnIndex Column index, when configuration <code>useColumnLabel</code> is <code>false
   *     </code>
   * @return the enum
   * @throws SQLException the SQL exception
   */
  @Override
  public @Nullable E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String value = rs.getString(columnIndex);
    if (rs.wasNull()) {
      return null;
    }
    return convertToEnum(value);
  }

  /**
   * When get value call this func for return enum
   *
   * @param cs CallableStatement
   * @param columnIndex Column index, when configuration <code>useColumnLabel</code> is <code>false
   *     </code>
   * @return the enum
   * @throws SQLException the SQL exception
   */
  @Override
  public @Nullable E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String value = cs.getString(columnIndex);
    if (cs.wasNull()) {
      return null;
    }
    return convertToEnum(value);
  }

  /**
   * Convert String to enum
   *
   * @param value value get from database
   * @return Enum after converted
   */
  private E convertToEnum(String value) {
    for (E enumValue : type.getEnumConstants()) {
      if (String.valueOf(enumValue.value()).equals(value)) {
        return enumValue;
      }
    }
    throw new IllegalArgumentException("Invalid enum value: " + value);
  }
}
