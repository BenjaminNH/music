package edu.hut.oyg.music.service.impl;

import edu.hut.oyg.music.dao.UserMapper;
import edu.hut.oyg.music.dto.admin.UserDTO;
import edu.hut.oyg.music.entity.User;
import edu.hut.oyg.music.service.UserService;
import edu.hut.oyg.music.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper mapper;

    @Override
    public boolean insert(User user) {
        return mapper.insert(user);
    }

    @Override
    public boolean delete(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public boolean update(User user) {
        return mapper.update(user);
    }

    @Override
    public boolean verifyPassword(String username, String password) {
        return mapper.verifyPassword(username,password) == 1;
    }

    @Override
    public List<User> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<User> selectByUsername(String username) {
        return mapper.selectByUsername(username);
    }

    @Override
    public User selectById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public List<UserDTO> selectDTO() {
        return mapper.selectDTO();
    }

    @Override
    public String saveFile(MultipartFile uploadFile) {
        String fileName = FileUtil.addTimeMillis(uploadFile.getOriginalFilename());
        String path = FileUtil.userDir + FileUtil.separator + "img" + FileUtil.separator + "userPic" + FileUtil.separator + fileName;
        String pic = "/img/userPic/" + fileName;
        boolean success = FileUtil.saveFile(uploadFile, path);
        return success ? pic : null;
    }
}
