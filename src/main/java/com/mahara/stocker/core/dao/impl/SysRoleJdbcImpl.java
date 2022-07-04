package com.mahara.stocker.core.dao.impl;

import com.mahara.stocker.core.dao.SysRoleJdbc;
import com.mahara.stocker.core.model.SysRole;
import com.mahara.stocker.util.JDBCTemplateUtil;
import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.response.PaginationOut;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SysRoleJdbcImpl implements SysRoleJdbc {
  @Autowired private JDBCTemplateUtil jtUtil;

  private RowMapper<SysRole> rowMapper =
      (rs, row) -> {
        SysRole bean = new SysRole();
        bean.setId(rs.getLong("id"));
        bean.setRole(rs.getString("role"));
        bean.setRoleName(rs.getString("role_name"));
        bean.setMemo(rs.getString("memo"));
        return bean;
      };

  @Override
  public PaginationOut<SysRole> search(String keyword, PaginationIn pi) {
    String anyPosition = "%" + keyword + "%";
    String endWith = "%" + keyword;
    String startWith = keyword + "%";

    StringBuilder sql = new StringBuilder("select * from sys_role where 1=1 ");
    MapSqlParameterSource params = new MapSqlParameterSource();
    if (!StringUtils.isEmpty(keyword)) {
      sql.append("and (role like :role or role_name like :roleName) ");
      params.addValue("role", anyPosition);
      params.addValue("roleName", anyPosition);
    }

    return jtUtil.queryForPagination(sql.toString(), params, rowMapper, pi);
  }

  @Override
  public List<SysRole> getByUserId(Long userId) {
    StringBuilder sql =
        new StringBuilder("select t.* from sys_role t ")
            .append("join user_role ur on ")
            .append("ur.role_id=t.id ")
            .append("and ur.user_id=:userId");

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("userId", userId);

    return jtUtil.npjt().query(sql.toString(), params, rowMapper);
  }

  @Override
  public Optional<SysRole> findByUniqueKey(String role, Long id) {
    StringBuilder sql = new StringBuilder("select * from sys_role where role=:role ");
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("role", role);
    if (id != null) {
      sql.append("AND id!=:id ");
      params.addValue("id", id);
    }
    var tmp = jtUtil.npjt().query(sql.toString(), params, rowMapper);
    if (tmp == null || tmp.size() == 0) {
      return Optional.ofNullable(null);
    } else {
      return Optional.ofNullable(tmp.get(0));
    }
  }
}
