package xyz.codedog.simple.mapper;

import xyz.codedog.simple.model.SysRole;
import xyz.codedog.simple.model.SysUser;

import java.util.List;

public interface UserMapper {
    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    SysUser selectById(Long id);

    /**
     * 查询全部用户
     * @return
     */
    List<SysUser> selectAll();

    /**
     * 根据用户 id 查询角色信息
     * @param userId
     * @return
     */
    List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    int insert(SysUser sysUser);

    /**
     * 新增用户-使用useGeneratedKeys方式
     * 只适用于支持主键自增的数据库
     * @param sysUser
     * @return
     */
    int insert2(SysUser sysUser);

    /**
     * 新增用户-使用selectKey方式
     * 既适用于支持主键自增的数据库,也适用于不支持主键自增的数据库
     * @param sysUser
     * @return
     */
    int insert3(SysUser sysUser);

}
