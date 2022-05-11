package edu.hut.oyg.music.dao;

import edu.hut.oyg.music.dto.admin.UserDTO;
import edu.hut.oyg.music.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    boolean insert(User user);
    boolean delete(Integer id);
    boolean update(User user);
    int verifyPassword(@Param("username") String username,@Param("password") String password);
    List<User> selectAll();
    List<User> selectByUsername(String username);
    User selectById(Integer id);
    List<UserDTO> selectDTO();
}
