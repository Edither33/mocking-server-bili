package bilibili.dao;

import bilibili.entity.auth.AuthElementOperation;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 权限控制--页面元素操作表(AuthElementOperation)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-02 11:08:22
 */
public interface AuthElementOperationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthElementOperation queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AuthElementOperation> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param AuthElementOperation 实例对象
     * @return 对象列表
     */
    List<AuthElementOperation> queryAll(AuthElementOperation AuthElementOperation);

    /**
     * 新增数据
     *
     * @param AuthElementOperation 实例对象
     * @return 影响行数
     */
    int insert(AuthElementOperation AuthElementOperation);

    /**
     * 修改数据
     *
     * @param AuthElementOperation 实例对象
     * @return 影响行数
     */
    int update(AuthElementOperation AuthElementOperation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}