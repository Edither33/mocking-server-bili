package bilibili.dao;

import bilibili.entity.auth.AuthRole;
import bilibili.entity.auth.AuthRoleElementOperation;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;

/**
 * 权限控制--角色表(AuthRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-02 11:08:34
 */
public interface AuthRoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRole queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AuthRole> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tAuthRole 实例对象
     * @return 对象列表
     */
    List<AuthRole> queryAll(AuthRole tAuthRole);

    /**
     * 新增数据
     *
     * @param tAuthRole 实例对象
     * @return 影响行数
     */
    int insert(AuthRole tAuthRole);

    /**
     * 修改数据
     *
     * @param tAuthRole 实例对象
     * @return 影响行数
     */
    int update(AuthRole tAuthRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    AuthRole getRoleByCodeId(String roleCode);
}