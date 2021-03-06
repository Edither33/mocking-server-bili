package bilibili.dao;

import bilibili.entity.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;

/**
 * 权限控制--角色页面菜单关联表(AuthRoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-02 11:08:34
 */
public interface AuthRoleMenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRoleMenu queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AuthRoleMenu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tAuthRoleMenu 实例对象
     * @return 对象列表
     */
    List<AuthRoleMenu> queryAll(AuthRoleMenu tAuthRoleMenu);

    /**
     * 新增数据
     *
     * @param tAuthRoleMenu 实例对象
     * @return 影响行数
     */
    int insert(AuthRoleMenu tAuthRoleMenu);

    /**
     * 修改数据
     *
     * @param tAuthRoleMenu 实例对象
     * @return 影响行数
     */
    int update(AuthRoleMenu tAuthRoleMenu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    List<AuthRoleMenu> getAuthRoleMenus(@Param("roleIdSet") Set<Long> roleIdList);
}