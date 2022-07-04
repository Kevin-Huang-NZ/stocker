package com.mahara.stocker.util;

import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.response.PaginationOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("jtUtil")
public class JDBCTemplateUtilMysqlImpl implements JDBCTemplateUtil {
  @Autowired private JdbcTemplate jdbcTmpl;
  @Autowired private NamedParameterJdbcTemplate npJdbcTmpl;

  @Override
  public JdbcTemplate jt() {
    return jdbcTmpl;
  }

  @Override
  public NamedParameterJdbcTemplate npjt() {
    return npJdbcTmpl;
  }

  @Override
  public <T> PaginationOut<T> queryForPagination(
      String sql,
      SqlParameterSource paramSource,
      RowMapper<T> rowMapper,
      PaginationIn paginationIn) {
    String countSql = "select count(1) from  ( " + sql + " ) count_view";
    int totalElements = npJdbcTmpl.queryForObject(countSql, paramSource, Integer.class);
    int totalPages = (totalElements + paginationIn.getSize() - 1) / paginationIn.getSize();

    // JPA的page number从0开始，改成一致的
    //        int offset = (paginationIn.getNumber() - 1) * paginationIn.getSize();
    int offset = paginationIn.getNumber() * paginationIn.getSize();
    int rowCount = paginationIn.getSize();
    StringBuilder pageSql = new StringBuilder(sql);
    pageSql.append(" limit ").append(offset).append(",").append(rowCount);
    List<T> data = npJdbcTmpl.query(pageSql.toString(), paramSource, rowMapper);

    PaginationOut paginationOut =
        new PaginationOut(paginationIn.getNumber(), paginationIn.getSize());
    paginationOut.setTotalElements(totalElements);
    paginationOut.setTotalPages(totalPages);
    paginationOut.setContent(data);
    return paginationOut;
  }

  public String generatedKeyName(String columnName) {
    return "GENERATED_KEY";
  }
}
