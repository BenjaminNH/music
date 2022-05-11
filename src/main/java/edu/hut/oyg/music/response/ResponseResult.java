package edu.hut.oyg.music.response;

import lombok.Data;

@Data
public class ResponseResult<T> {
    private boolean success;
    private T data;
    private int code;
    private String message;
}
