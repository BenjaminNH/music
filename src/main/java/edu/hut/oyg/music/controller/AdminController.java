package edu.hut.oyg.music.controller;

import edu.hut.oyg.music.response.ResponseCode;
import edu.hut.oyg.music.response.ResponseResult;
import edu.hut.oyg.music.response.ResponseResultFactory;
import edu.hut.oyg.music.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/admin/login")
    public ResponseResult<String> login(@RequestParam("name") String name,
                                        @RequestParam("password") String password,
                                        HttpSession session) {
        boolean correct = adminService.verify(name, password);
        ResponseResult<String> result;
        if (correct) {
            result = ResponseResultFactory.genSuccessResult(name,"success");
            session.setAttribute("name",name);
        } else {
            result = ResponseResultFactory.genFailResult(null,"name or password error");
        }
        return result;
    }

    @RequestMapping("/preload")
    public ResponseResult<String> preload(HttpSession session) {
        return ResponseResultFactory.genSuccessResult("preload","message");
    }

}
/*
 * 1. 空方法 100ms左右 ---> 只是启动后第一次请求 ---> 通过预热解决，启动后通过RestTemplate先发送一次请求
 * 2. + session 350ms, log输出初始化session 254ms ---> Tomcat问题，通过切换undertow解决
 * 3. + 数据库 300ms --> 数据库200ms ---> 通过预热解决，启动后查询一次数据库
 * 4. + 数据库 + session 600ms (min-idle无效)
 */
