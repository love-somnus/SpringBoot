package com.somnus.apache;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    private Date birthday;
    private BigDecimal money;

    private Pet pet;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Data
    @AllArgsConstructor
    static class Pet implements Serializable {
        private static final long serialVersionUID = 1L;

        private String name;

    }
}
