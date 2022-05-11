package edu.hut.oyg.music.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Byte sex;
    private String phoneNum;
    private String email;
    private Date birth;
    private String introduction;
    private String location;
    private String pic;
    private Date createTime;
    private Date updateTime;
}
