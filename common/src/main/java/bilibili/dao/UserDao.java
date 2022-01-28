package bilibili.dao;

import bilibili.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User queryById(Long id);

    User queryUserByPhone(String phone);

    Integer insertUser(User user);
}
