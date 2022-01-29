package bilibili.dao;

import bilibili.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User queryById(Long id);

    User queryUserByPhone(String phone);

    Integer insertUser(User user);

    Integer update(User user);

    User queryUserByEmail(String email);
}
