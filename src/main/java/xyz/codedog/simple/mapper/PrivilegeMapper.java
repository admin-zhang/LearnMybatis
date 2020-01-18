package xyz.codedog.simple.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import xyz.codedog.simple.model.SysPrivilege;

/**
 * Provider注解
 */
public interface PrivilegeMapper {

    @SelectProvider(type = PrivilegeProvider.class,method = "selectById")
    SysPrivilege selectById(Long id);
}
