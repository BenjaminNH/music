package edu.hut.oyg.music.util;

import edu.hut.oyg.music.constant.ResponseCode;

import java.util.HashMap;

public class ResponseResult extends HashMap<String, Object> {
    public static final String CODE_TAG = "code";
    public static final String MESSAGE_TAG = "message";
    public static final String DATA_TAG = "data";

    ResponseResult() {}

    ResponseResult(int code, String message) {
        put(CODE_TAG, code);
        put(MESSAGE_TAG, message);
    }

    ResponseResult(int code, String message, Object data) {
        put(CODE_TAG, code);
        put(MESSAGE_TAG, message);
        put(DATA_TAG, data);
    }

    public static ResponseResult success(String message) {
        return new ResponseResult(ResponseCode.SUCCESS, message);
    }

    public static ResponseResult success(String message, Object data) {
        return new ResponseResult(ResponseCode.SUCCESS, message, data);
    }

    public static ResponseResult fail(String message) {
        return new ResponseResult(ResponseCode.INTERNAL_ERROR, message);
    }

    public static ResponseResult fail(int code, String message) {
        return new ResponseResult(code, message);
    }

    public static ResponseResult fail(int code, String message, Object data) {
        return new ResponseResult(code, message, data);
    }
}
