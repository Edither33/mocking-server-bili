package bilibili.dao;

import bilibili.entity.auth.AuthRoleElementOperation;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 权限控制--角色与元素操作关联表(AuthRoleElementOperation)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-02 11:08:34
 */
public interface AuthRoleElementOperationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRoleElementOperation queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AuthRoleElementOperation> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tAuthRoleElementOperation 实例对象
     * @return 对象列表
     */
    List<AuthRoleElementOperation> queryAll(AuthRoleElementOperation tAuthRoleElementOperation);

    /**
     * 新增数据
     *
     * @param tAuthRoleElementOperation 实例对象
     * @return 影响行数
     */
    int insert(AuthRoleElementOperation tAuthRoleElementOperation);

    /**
     * 修改数据
     *
     * @param tAuthRoleElementOperation 实例对象
     * @return 影响行数
     */
    int update(AuthRoleElementOperation tAuthRoleElementOperation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}