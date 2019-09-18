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
 * @ClassName: Transaction3rdService
 * @Description: 两个都插入了数据库
 * 	看到这里很多小伙伴都可能会问，按照我们的逻辑来想的话child（）中抛出了异常，
 * parent（）没有抛出并且捕获了child（）抛出了异常！执行的结果应该是child（）回滚，parent（）提交成功的啊！			 
 * @author Somnus
 * @date 2018年9月17日
 */
@Service
public class Transaction3rdService {
    @Autowired
    private TCityMapper cmapper;

    @Autowired
    private TLogMapper lmapper;

    /**
     * @Transactional
     * public void parent() {
     *   cmapper.insert(new TCity("九江","庐山天下悠"));
     *   try{
     *     lmapper.insert(new TLog("飞流直下三千尺, 疑是银河落九天"));
     *     System.out.println(1/0);
     *   }catch(Exception e){
     *     e.printStackTrace();
     *   }
     * }
     */
    @Transactional
    public void parent() {
        cmapper.insert(new TCity("九江","庐山天下悠"));
        try{
            child();
        }catch(Exception e){
            e.printStackTrace();
        }
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
