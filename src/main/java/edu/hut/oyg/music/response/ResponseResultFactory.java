package edu.hut.oyg.music.response;

public class ResponseResultFactory {
    public static <T> ResponseResult<T> genResult(boolean success, T data, ResponseCode code,String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setSuccess(success);
        result.setData(data);
        result.setMessage(message);
        result.setCode(code.getCode());
        return result;
    }

    public static <T> ResponseResult<T> genSuccessResult(T data, String message) {
        return genResult(true,data,ResponseCode.SUCCESS,message);
    }

    public static <T> ResponseResult<T> genFailResult(T data,String message) {
        return genResult(true,data,ResponseCode.FAIL,message);
    }
}
