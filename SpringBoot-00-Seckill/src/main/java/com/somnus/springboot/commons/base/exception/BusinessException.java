/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.somnus.springboot.commons.base.exception;

import java.text.MessageFormat;

import com.somnus.springboot.commons.base.enums.ErrorCodeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: BusinessException
 * @Description: 业务异常.
 * @author Somnus
 * @date 2018年9月26日
 */
public class BusinessException extends RuntimeException {

    /**
     * 异常码
     */
    @Setter
    @Getter
    protected Integer code;

    private static final long serialVersionUID = 3160241586346324994L;

    public BusinessException() {
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String msgFormat, Object... args) {
        super(MessageFormat.format(msgFormat, args));
        this.code = code;
    }

    public BusinessException(ErrorCodeEnum codeEnum, Object... args) {
        super(MessageFormat.format(codeEnum.getMessage(), args));
        this.code = codeEnum.getCode();
    }
}
