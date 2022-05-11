package edu.hut.oyg.music.service;

import edu.hut.oyg.music.dto.admin.UserDTO;
import edu.hut.oyg.music.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    boolean insert(User user);
    boolean delete(Integer id);
    boolean update(User user);
    boolean verifyPassword(String username,String password);
    List<User> selectAll();
    List<User> selectByUsername(String username);
    User selectById(Integer id);
    List<UserDTO> selectDTO();
    String saveFile(MultipartFile uploadFile);
}
