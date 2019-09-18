package com.somnus.spingboot.rabbitmq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2019/1/16 23:02
 */
@Data
@AllArgsConstructor
@ToString
public class Order implements Serializable {

    private static final long serialVersionUID = -8878258444390345489L;

    private Integer id;

    private String messageId;
}
