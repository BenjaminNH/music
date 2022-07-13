package edu.hut.oyg.music.excption;

import edu.hut.oyg.music.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseResult handleServiceException(ServiceException e) {
        log.error("ServiceException: {}", e.getMessage());
        Integer code = e.getCode();
        return ObjectUtils.isEmpty(code) ? ResponseResult.fail(e.getMessage()) : ResponseResult.fail(code, e.getMessage());
    }
}
