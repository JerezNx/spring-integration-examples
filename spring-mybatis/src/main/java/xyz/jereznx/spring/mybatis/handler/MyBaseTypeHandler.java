package xyz.jereznx.spring.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import xyz.jereznx.spring.mybatis.domain.BaseEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author liqilin
 * @since 2020/11/28 14:39
 */
@Slf4j
public class MyBaseTypeHandler<E extends BaseEnum> extends BaseTypeHandler<E> {

    private E[] enums;

    public MyBaseTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        log.debug("index : {}, parameter : {},jdbcType : {} ", i, parameter.getCode(), jdbcType);
        if (jdbcType == null) {
            ps.setObject(i, parameter.getCode());
        } else {
            ps.setObject(i, parameter.getCode(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object code = rs.getObject(columnName);
        if (rs.wasNull()) {
            return null;
        }
        return getEnmByCode(code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object code = rs.getObject(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        return getEnmByCode(code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object code = cs.getObject(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        return getEnmByCode(code);
    }

    private E getEnmByCode(Object code) {
        if (code == null) {
            throw new NullPointerException("the result code is null ");
        }
        if (code instanceof Integer) {
            for (E e : enums) {
                if (code.equals(e.getCode())) {
                    return e;
                }
            }
            throw new IllegalArgumentException("Unknown enumeration type , please check the enumeration code :  " + code);
        }
        if (code instanceof Byte) {
            Integer intCode = ((Byte) code).intValue();
            for (E e : enums) {
                if (intCode.equals(e.getCode())) {
                    return e;
                }
            }
            throw new IllegalArgumentException("Unknown enumeration type , please check the enumeration code :  " + code);
        }
        if (code instanceof String) {
            try {
                Integer intCode = Integer.parseInt((String) code);
                for (E e : enums) {
                    if (intCode.equals(e.getCode())) {
                        return e;
                    }
                }
            } catch (NumberFormatException e) {
                for (E e1 : enums) {
                    if (code.equals(e1.getMessage())) {
                        return e1;
                    }
                }
            }
            throw new IllegalArgumentException("Unknown enumeration type , please check the enumeration code :  " + code);
        }
        throw new IllegalArgumentException("Unknown enumeration type , please check the enumeration code :  " + code);
    }
}
