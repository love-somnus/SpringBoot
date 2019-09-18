package com.somnus.springboot.commons.base.enums;

/**
 * @ClassName: ResultCodeEnum
 * @Description: 消息响应枚举
 * @author Somnus
 * @date 2018年8月27日
 */
public enum ResultCodeEnum {

    SEC_KILL_MUCH("2","哎呦喂，人也太多了，请稍后！"),
    SEC_KILL_SUCCESS("1","秒杀成功"),
    SEC_KILL_END("0","秒杀结束"),
    REPEAT_KILL("-1","重复秒杀"),
    INNER_ERROR("-2","系统异常"),
    DATE_REWRITE("-3","数据篡改");

    private final String code;

    private final String message;

    private ResultCodeEnum(String code,String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
    /**
     * 通过枚举<code>code</code>获得枚举
     * 
     * @param code
     * @return
     */
    public static ResultCodeEnum getByCode(String code) {
        for (ResultCodeEnum renum : values()) {
            if (renum.getCode().equals(code)) {
                return renum;
            }
        }
        return null;
    }
    
    public static ResultCodeEnum nameOf(String name){
        ResultCodeEnum renum = null;
        if (name != null){
            for (ResultCodeEnum type : ResultCodeEnum.values()) {
                if (type.name().equalsIgnoreCase(name))
                    renum = type;
            }
        }
        return renum;
    }
    
    public static ResultCodeEnum msgOf(String message){
        ResultCodeEnum renum = null;
        if (message != null){
            for (ResultCodeEnum type : ResultCodeEnum.values()) {
                if (type.message.equalsIgnoreCase(message))
                    renum = type;
            }
        }
        return renum;
    }

}
