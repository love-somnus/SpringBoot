/**
 * <p>Title: CityServiceImpl.java </p>
 * <p>Description: TODO(用一句话描述该文件做什么) </p>
 *
 * @author pc
 * @version 1.0.0
 * @date 2018年9月13日
 */
package com.somnus.springboot.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.somnus.springboot.mybatis.mapper.TCityMapper;
import com.somnus.springboot.mybatis.model.TCity;

/**
 * @ClassName: CityServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author pc
 * @date 2018年9月13日
 */
@Service
public class TransactionService {

    @Autowired
    private TCityMapper mapper;

    /**
     * 不回滚
     */
    public void inserSuccess1st() {
        mapper.insert(new TCity("九江","庐山天下悠"));
        throw new IllegalArgumentException();
    }

    /**
     * 加了注解回滚
     */
    @Transactional
    public void inserFail1st() {
        mapper.insert(new TCity("九江","庐山天下悠"));
        throw new IllegalArgumentException();
    }

    /**
     * 加了注解也不会回滚，因为不是RuntimeException
     * @throws Exception
     */
    @Transactional
    public void inserSuccess2nd() throws Exception {
        mapper.insert(new TCity("九江","庐山天下悠"));
        throw new IOException();
    }

    /**
     * 想让Exception异回滚，需要指定
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public void inserFail2nd() throws Exception {
        mapper.insert(new TCity("九江","庐山天下悠"));
        throw new IOException();
    }

}
