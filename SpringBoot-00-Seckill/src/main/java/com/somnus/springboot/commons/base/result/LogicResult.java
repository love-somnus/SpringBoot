package com.somnus.springboot.commons.base.result;

import com.alibaba.fastjson.JSONObject;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

import org.springframework.util.StringUtils;

/**
 * @ClassName: LogicResult
 * @Description: 响应结果类
 * @author Somnus
 * @date 2018年8月24日
 * @param <T>
 */
@Data
@Builder
public class LogicResult<T> implements Serializable {

    private static final long serialVersionUID = 5472588435205050617L;

    @Tolerate
    public LogicResult() {
    }

    /**
     * 响应码
     */
    @Builder.Default
    private Integer resCode = LogicResult.SUCCESS_CODE;
    /**
     * 响应消息
     */
    @Builder.Default
    private String resMsg = LogicResult.SUCCESS_MESSAGE;
    /**
     * 响应数据
     */
    private T result;
    /**
     * 成功
     */
    public static final Integer SUCCESS_CODE = 200;
    /**
     * 异常
     */
    public static final String SUCCESS_MESSAGE = "处理成功";
    /**
     * 失败
     */
    public static final Integer FAIL_CODE = 500;
    /**
     * 异常
     */
    public static final Integer ERROR_CODE = 400;

    /**
     * 成功结果响应
     */
    public LogicResult<T> ok() {
        return this;
    }

    /**
     * 成功结果响应
     *
     * @param result
     */
    public LogicResult<T> success(T result) {
        this.result = result;
        return this;
    }

    /**
     * 失败结果响应
     *
     * @param failMsg
     */
    public LogicResult<T> error(String failMsg) {
        this.resCode = ERROR_CODE;
        this.resMsg = StringUtils.isEmpty(failMsg) ? "请求处理失败!" : failMsg;
        return this;
    }

    /**
     * 异常结果响应
     *
     * @param errorMsg
     */
    public LogicResult<T> fail(int resCode, String errorMsg) {
        this.resCode = resCode;
        this.resMsg = StringUtils.isEmpty(errorMsg) ? "请求处理异常!" : errorMsg;
        return this;
    }

    /**
     * 异常结果响应
     */
    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }
}
