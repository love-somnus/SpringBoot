package com.somnus.designPatterns.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Title: WeeklyLog3.java
 * @Package com.somnus.designPatterns.prototype
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月25日 下午1:33:15
 * @version V1.0
 */
@Data
@Builder
public class WeeklyLog3 implements Serializable {
	private Attachment3 attachment;
    private String name;
    private String date;
    private String content;
    
    // 使用序列化技术实现深克隆
    @SneakyThrows
    public WeeklyLog3 deepClone() {
        // 将对象写入流中
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);

        // 将对象从流中取出
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (WeeklyLog3) ois.readObject();
    }

    @Data
    @Builder
    static class Attachment3 implements Serializable {
    	private String name; // 附件名

    }
}
