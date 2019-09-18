/**
 * <p>Title: TransactionService.java </p>
 * <p>Description: TODO(用一句话描述该文件做什么) </p>
 *
 * @author pc
 * @version 1.0.0
 * @date 2018年9月17日
 */
package com.somnus.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.somnus.springboot.mybatis.mapper.TCityMapper;
import com.somnus.springboot.mybatis.mapper.TLogMapper;
import com.somnus.springboot.mybatis.model.TCity;
import com.somnus.springboot.mybatis.model.TLog;

/**
 * @ClassName: Transaction1stService
 * @Description: 用来测试事务失效的坑
 * @author Somnus
 * @date 2018年9月17日
 */
@Service
public class Transaction1stService {
    @Autowired
    private TCityMapper cmapper;

    @Autowired
    private TLogMapper lmapper;

    @Transactional
    public void parent() {
        cmapper.insert(new TCity("九江","庐山天下悠"));
        child() ;
    }

    /**
     * parent如果没有事务，child就开启一个事务
     * parent如果有事务，方法parent所在的事务就会挂起，方法child会起一个新的事务
     * 				  待方法child的事务完成以后，方法parent才继续执行
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void child() {
        lmapper.insert(new TLog("飞流直下三千尺, 疑是银河落九天"));
    }
}
