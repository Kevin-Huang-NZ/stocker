package com.mahara.stocker.core.dao.impl;

import com.mahara.stocker.core.dao.SysPermissionJdbc;
import com.mahara.stocker.core.model.SysPermission;
import com.mahara.stocker.util.JDBCTemplateUtil;
import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.response.PaginationOut;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SysPermissionJdbcImpl implements SysPermissionJdbc {
  private final RowMapper<SysPermission> rowMapper =
      (rs, row) -> {
        SysPermission bean = new SysPermission();
        bean.setId(rs.getLong("id"));
        bean.setPermission(rs.getString("permission"));
        bean.setPermissionName(rs.getString("permission_name"));
        bean.setMemo(rs.getString("memo"));
        return bean;
      };
  @Autowired private JDBCTemplateUtil jtUtil;

  @Override
  public PaginationOut<SysPermission> search(String keyword, PaginationIn pi) {
    String anyPosition = "%" + keyword + "%";
    String endWith = "%" + keyword;
    String startWith = keyword + "%";

    StringBuilder sql = new StringBuilder("select * from sys_permission where 1=1 ");
    MapSqlParameterSource params = new MapSqlParameterSource();
    if (!StringUtils.isEmpty(keyword)) {
      sql.append("and (permission like :permission or permission_name like :permissionName) ");
      params.addValue("permission", startWith);
      params.addValue("permissionName", anyPosition);
    }
    sql.append("order by permission ");

    return jtUtil.queryForPagination(sql.toString(), params, rowMapper, pi);
  }

  @Override
  public List<SysPermission> getByRoleIds(List<Long> roleIds) {
    if (roleIds == null || roleIds.isEmpty()) {
      return new ArrayList<SysPermission>();
    }
    StringBuilder sql =
        new StringBuilder("select t.* from sys_permission t ")
            .append("join sys_role_permission rp on ")
            .append("rp.permission_id=t.id ")
            .append("and rp.role_id in (:roleIds)");

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("roleIds", roleIds);

    return jtUtil.npjt().query(sql.toString(), params, rowMapper);
  }

  @Override
  public Optional<SysPermission> findByUniqueKey(String permission, Long id) {
    StringBuilder sql =
        new StringBuilder("select * from sys_permission where permission=:permission ");
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("permission", permission);
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
