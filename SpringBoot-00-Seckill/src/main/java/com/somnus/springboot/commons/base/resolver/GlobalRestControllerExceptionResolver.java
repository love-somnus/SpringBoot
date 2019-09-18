package com.somnus.springboot.commons.base.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.somnus.springboot.commons.base.enums.ErrorCodeEnum;
import com.somnus.springboot.commons.base.exception.BusinessException;
import com.somnus.springboot.commons.base.exception.ParamValidException;
import com.somnus.springboot.commons.base.exception.UserNotLoginException;
import com.somnus.springboot.commons.base.result.LogicResult;

/**
 * @ClassName: GlobalRestControllerExceptionResolver
 * @Description: 异常处理类
 * @author Somnus
 * @date 2018年8月24日
 */
@Order(1)
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalRestControllerExceptionResolver {

    /**
     * 业务异常报错
     *
     * @param e 业务异常
     */
    @ExceptionHandler(value = BusinessException.class)
    public LogicResult<String> allServiceExceptionHandler(BusinessException e) {
        log.warn("业务处理失败:" + e.getMessage());
        return LogicResult.<String>builder().build().fail(e.getCode() == 0 ? LogicResult.ERROR_CODE : e.getCode(), e.getMessage());
    }
    
    /**
     * 所有异常报错
     *
     * @param e 业务异常
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public LogicResult<String> allIllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.warn("业务参数检查未通过:" + e.getMessage());
        return LogicResult.<String>builder().build().fail(ErrorCodeEnum.GL99990100.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常报错
     *
     * @param e 参数校验异常
     */
    @ExceptionHandler(value = ParamValidException.class)
    public LogicResult<String> paramValidException(ParamValidException e) {
    	log.warn("业务参数检查未通过:" + e.getMessage());
        return LogicResult.<String>builder().build().fail(ErrorCodeEnum.GL99990100.getCode(), e.getMessage());
    }

    /**
     * 所有异常报错
     *
     * @param e 业务异常
     */
    @ExceptionHandler(value = UserNotLoginException.class)
    public LogicResult<String> allUserNotLoginExceptionHandler(UserNotLoginException e) {
        log.warn("用户未登录!");
        return LogicResult.<String>builder().build().error("用户未登录!");
    }

    /**
     * 所有异常报错
     *
     * @param e 业务异常
     */
    @ExceptionHandler(value = Exception.class)
    public LogicResult<String> allExceptionHandler(Exception e) {
        log.error("业务处理异常:", e);
        return LogicResult.<String>builder().build().error("系统打盹中，您的请求暂时无法处理。");
    }
}
