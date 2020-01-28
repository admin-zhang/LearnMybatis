package xyz.codedog.simple.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.codedog.simple.model.SysRole;
import xyz.codedog.simple.model.SysUser;

import java.util.List;

public interface UserMapper {
    /**
     * 通过id查询用户
     *
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
     *
     * @param userId
     * @return
     */
    List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 新增用户
     *
     * @param sysUser
     * @return
     */
    int insert(SysUser sysUser);

    /**
     * 新增用户-使用useGeneratedKeys方式
     * 只适用于支持主键自增的数据库
     *
     * @param sysUser
     * @return
     */
    int insert2(SysUser sysUser);

    /**
     * 新增用户-使用selectKey方式
     * 既适用于支持主键自增的数据库,也适用于不支持主键自增的数据库
     *
     * @param sysUser
     * @return
     */
    int insert3(SysUser sysUser);

    /**
     * 根据主键更新
     *
     * @param sysUser
     * @return
     */
    int updateById(SysUser sysUser);

    /**
     * 通过主键删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    int deleteById(SysUser sysUser);

    /**
     * 根据用户 id 和角色的 enabled 状态获取用户的角色
     *
     * @param userId
     * @param enabled
     * @return
     */
    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId,@Param("enabled") Integer enabled);

/* ====== 动态SQL语句 =====*/

  /* ============================================= if 的用法 =============================================*/

    /**
     * 根据动态条件查询用户信息
     *
     * @param sysUser
     * @return
     */
    List<SysUser> selectByUser(SysUser sysUser);

    /**
     * 根据主键更新
     *
     * @param sysUser
     * @return
     */
    int updateByIdSelective(SysUser sysUser);

    /**
     * 使用动态语句if
     *
     * @param sysUser
     * @return
     */
    int insert4(SysUser sysUser);

    /* =========================================== choose 的用法 ===========================================*/

    /**
     * 根据用户 id 或用户名查询
     *
     * @param sysUser
     * @return
     */
    SysUser selectByIdOrUserName(SysUser sysUser);

    /**
     * 使用 where
     *
     * @param sysUser
     * @return
     */
    List<SysUser> selectByUser1(SysUser sysUser);
}
