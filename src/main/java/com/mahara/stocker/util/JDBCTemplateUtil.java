package com.mahara.stocker.util;

import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.response.PaginationOut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public interface JDBCTemplateUtil {
  JdbcTemplate jt();

  NamedParameterJdbcTemplate npjt();

  <T> PaginationOut<T> queryForPagination(
      String sql,
      SqlParameterSource paramSource,
      RowMapper<T> rowMapper,
      PaginationIn paginationIn);

  String generatedKeyName(String columnName);
}
