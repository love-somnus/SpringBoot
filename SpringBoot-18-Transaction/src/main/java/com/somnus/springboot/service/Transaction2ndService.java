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
 * @ClassName: Transaction2ndService
 * @Description: 会出现异常，并且数据都没有插入进去
 * child()抛出了异常，但是parent()没有抛出异常，按道理是不是应该parent()提交成功而child()回滚？
 * 
 * 可能有的小伙伴要说了，child()抛出了异常在parent()没有进行捕获，造成了parent()也是抛出了异常了的！所以他们两个都会回滚！
 * @author Somnus
 * @date 2018年9月17日
 */
@Service
public class Transaction2ndService {
    @Autowired
    private TCityMapper cmapper;

    @Autowired
    private TLogMapper lmapper;

    /**
     * @Transactional
     * public void parent() {
     *   cmapper.insert(new TCity("九江","庐山天下悠"));
     *   lmapper.insert(new TLog("飞流直下三千尺, 疑是银河落九天"));
     *   System.out.println(1/0);
     * }
     */
    @Transactional
    public void parent() {
        cmapper.insert(new TCity("九江","庐山天下悠"));
        child();
    }
    /**
     * parent如果没有事务，child就开启一个事务
     * parent如果有事务，方法parent所在的事务就会挂起，方法child会起一个新的事务
     * 				  待方法child的事务完成以后，方法parent才继续执行
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void child() {
        lmapper.insert(new TLog("飞流直下三千尺, 疑是银河落九天"));
        System.out.println(1/0);
    }
}
