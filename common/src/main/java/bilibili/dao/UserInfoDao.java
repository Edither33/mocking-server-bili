package bilibili.dao;

import bilibili.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户基本信息表(UserInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-01-28 11:50:02
 */
public interface UserInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param UserInfo 实例对象
     * @return 对象列表
     */
    List<UserInfo> queryAll(UserInfo UserInfo);

    /**
     * 新增数据
     *
     * @param UserInfo 实例对象
     * @return 影响行数
     */
    int insert(UserInfo UserInfo);

    /**
     * 修改数据
     *
     * @param UserInfo 实例对象
     * @return 影响行数
     */
    int update(UserInfo UserInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}