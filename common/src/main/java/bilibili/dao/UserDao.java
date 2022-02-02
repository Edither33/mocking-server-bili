package bilibili.dao;

import bilibili.entity.RefreshToken;
import bilibili.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    User queryById(Long id);

    User queryUserByPhone(String phone);

    Integer insertUser(User user);

    Integer update(User user);

    User queryUserByEmail(String email);

    Integer deleteRefreshToken(Long userId);

    Integer saveRefreshToken(RefreshToken refreshToken);

    RefreshToken queryByRefreshToken(String refreshToken);
}
