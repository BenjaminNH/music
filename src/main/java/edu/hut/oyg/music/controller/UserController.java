package edu.hut.oyg.music.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hut.oyg.music.dto.admin.UserDTO;
import edu.hut.oyg.music.entity.User;
import edu.hut.oyg.music.response.ResponseResult;
import edu.hut.oyg.music.response.ResponseResultFactory;
import edu.hut.oyg.music.service.UserService;
import edu.hut.oyg.music.constant.PageConstants;
import edu.hut.oyg.music.util.FileUtil;
import edu.hut.oyg.music.util.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService service;

    //TODO: 修改为默认图片
    private static final String DEFAULT_PIC = "/img/singerPic/2.png";

    @PostMapping("/user")
    public ResponseResult<User> addUser(User user) {
        if (service.selectByUsername(user.getUsername()) != null) {
            return ResponseResultFactory.genFailResult(null,"username already exist");
        }
        ResponseResult<User> result;
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        boolean success = service.insert(user);
        if (success) {
            result = ResponseResultFactory.genSuccessResult(user,"add success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"add fail");
        }
        return result;
    }

    @DeleteMapping("/user/{id}")
    public ResponseResult<Object> deleteUser(@PathVariable("id") Integer id) {
        ResponseResult<Object> result;
        boolean success = service.delete(id);
        if (success) {
            result = ResponseResultFactory.genSuccessResult(null,"delete success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"delete fail");
        }
        return result;
    }

    @PutMapping("/user")
    public ResponseResult<User> updateUser(User user) {
        ResponseResult<User> result;
        user.setUpdateTime(new Date());
        boolean success = service.update(user);
        if (success) {
            user = service.selectById(user.getId());
            result = ResponseResultFactory.genSuccessResult(user,"update success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"update fail");
        }
        return result;
    }

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(format,true);
        binder.registerCustomEditor(Date.class,editor);
    }

    @PostMapping("/user/login")
    public ResponseResult<String> verifyPwd(@RequestParam("username") String username,
                                         @RequestParam("password") String password) {
        ResponseResult<String> result;
        boolean success = service.verifyPassword(username,password);
        if (success) {
            result = ResponseResultFactory.genSuccessResult(username,"login success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"login fail");
        }
        return result;
    }

    @GetMapping(value = {"/user","/user/{username}"})
    public ResponseResult<Map<String,Object>> selectList(@PathVariable(value = "username",required = false) String username,
                                                        @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "pageSize",defaultValue = PageConstants.DEFAULT_PAGE_SIZE) Integer pageSize) {
        ResponseResult<Map<String,Object>> result;
        PageHelper.startPage(pageNo,pageSize);
        List<User> users = username == null ? service.selectAll() : service.selectByUsername(username);
        PageInfo<User> info = new PageInfo<>(users);
        if (info.getList() != null) {
            result = ResponseResultFactory.genSuccessResult(PageRespUtil.getRes(info,"user"),"get success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"get fail");
        }
        return result;
    }

    @GetMapping("/admin/user-info")
    public ResponseResult<List<UserDTO>> selectSex() {
        ResponseResult<List<UserDTO>> result;
        List<UserDTO> userDTO = service.selectDTO();
        if (userDTO != null) {
            result = ResponseResultFactory.genSuccessResult(userDTO,"get success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"get fail");
        }
        return result;
    }

    @PostMapping("/user/{id}/pic")
    public ResponseResult<User> updateUserPic(@PathVariable("id") Integer id,
                                              @RequestParam("file") MultipartFile uploadFile) {
        ResponseResult<User> result;
        User user = service.selectById(id);
        if (!DEFAULT_PIC.equals(user.getPic())) {
            boolean deleted = FileUtil.deleteFile(user.getPic());
            if (!deleted) {
                log.warn("delete user pic fail");
            }
        }
        String pic = service.saveFile(uploadFile);
        if (pic != null) {
            user.setPic(pic);
            user.setUpdateTime(new Date());
            boolean updated = service.update(user);
            if (updated) {
                result = ResponseResultFactory.genSuccessResult(user, "update success");
            } else {
                result = ResponseResultFactory.genFailResult(null,"update fail");
            }
        } else {
            result = ResponseResultFactory.genFailResult(null,"save file fail");
        }
        return result;
    }
}
