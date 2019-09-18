package com.somnus.copy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/13 12:39
 */
@Data
@NoArgsConstructor
public class People {
    private String username;
    private String password;

    private Date birthday;
    private BigDecimal money;

    private List<String> photos;

    private List<Pet> pets;

    public People(String username, String password, Date birthday,
                  BigDecimal money) {
        super();
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.money = money;
    }

    @AllArgsConstructor
    public static class Pet{
        private String name;
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
