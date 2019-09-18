/**
 * <p>Title: AtomikosService.java </p>
 * <p>Description: TODO(用一句话描述该文件做什么) </p>
 *
 * @author pc
 * @version 1.0.0
 * @date 2018年9月20日
 */
package com.somnus.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.somnus.springboot.mapper.primary.CityMapper;
import com.somnus.springboot.mapper.secondary.LogMapper;

/**
 * @ClassName: AtomikosService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author pc
 * @date 2018年12月20日
 */
@Service
public class AtomikosService {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private LogMapper logMapper;

    @Transactional
    public void saveSuccess() {
        cityMapper.saveCity("九江", "庐山天下悠");
        logMapper.saveLog("飞流直下三千尺");
    }

    @Transactional
    public void saveFail() {
        cityMapper.saveCity("九江", "庐山天下悠");
        logMapper.saveLog("飞流直下三千尺");
        System.out.println(1/0);
    }

    @Transactional
    public void initData() {
        cityMapper.deleteAll();
        logMapper.deleteAll();
    }

}
