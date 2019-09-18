/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.somnus.springboot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableMap;
import com.somnus.springboot.mybatis.mapper.TCityMapper;
import com.somnus.springboot.mybatis.model.TCity;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: MybatisTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年10月22日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MybatisTest {

    @Autowired
    private TCityMapper mapper;

    @Test
    public void selectByCondition() {
        TCity city = mapper.selectByCondition(ImmutableMap.of("cityName", "鹰潭"));
        System.out.println(city);
    }

    @Test
    public void selectByCondition2() {
        TCity city = mapper.selectByCondition2(ImmutableMap.of("cityName", "鹰潭"));
        System.out.println(city);
    }

    @Test
    public void example() {
        Example example = new Example(TCity.class);
        example.orderBy("id").desc().orderBy("cityName").orderBy("description").asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cityName", "鹰潭");
        List<TCity> list = mapper.selectByExample(example);
        System.out.println(list);
    }

    @Test
    public void returnMap() {
        /* {1=TCity(id=1, cityName=鹰潭, description=龙虎天下绝)} */
        Map<String,Object> map = mapper.returnMap("鹰潭");
        System.out.println(map);
        List<Object> list = new ArrayList<>();
        for(Entry<String, Object> entry:map.entrySet()) {
            list.add(entry.getValue());
        }
        System.out.println(list);
    }

    @Test
    public void returnMap2() {
        /* {1={city_name=鹰潭, description=龙虎天下绝, id=1}} */
        Map<String,Object> map = mapper.returnMap2("鹰潭");
        System.out.println(map);
        List<Object> list = new ArrayList<>();
        for(Entry<String, Object> entry:map.entrySet()) {
            list.add(entry.getValue());
        }
        System.out.println(list);
    }

    @Test
    public void returnMap3() {
        List<Map<String,Object>> list = mapper.returnMap3("鹰潭");
        System.out.println(list);
    }

    @Test
    public void PageInfo() {
        PageHelper.startPage(1,10);
        Example example = new Example(TCity.class);
        example.orderBy("id").desc().orderBy("cityName").orderBy("description").asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cityName", "鹰潭");
        List<TCity> list = mapper.selectByExample(example);
        PageInfo<TCity> pageInfo = new PageInfo<>(list);
        System.out.println(JSON.toJSONString(pageInfo));
    }

    /** jdk6,7用法，创建接口 */
    @Test
    public void PageInfo2() {
        PageInfo<TCity> pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                Example example = new Example(TCity.class);
                example.orderBy("id").desc().orderBy("cityName").orderBy("description").asc();
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("cityName", "鹰潭");
                mapper.selectByExample(example);
            }
        });
        System.out.println(JSON.toJSONString(pageInfo));
    }
    /** jdk8 lambda用法  */
    @Test
    public void PageInfo3() {
        PageInfo<TCity> pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(()-> {
            Example example = new Example(TCity.class);
            example.orderBy("id").desc().orderBy("cityName").orderBy("description").asc();
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("cityName", "鹰潭");
            mapper.selectByExample(example);
        });
        System.out.println(JSON.toJSONString(pageInfo));
    }

    /** jdk6,7用法，创建接口 */
    @Test
    public void Page() {
        Page<TCity> page = PageHelper.startPage(1, 10).doSelectPage(new ISelect() {
            @Override
            public void doSelect() {
                Example example = new Example(TCity.class);
                example.orderBy("id").desc().orderBy("cityName").orderBy("description").asc();
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("cityName", "鹰潭");
                mapper.selectByExample(example);
            }
        });
        System.out.println(page);
    }
    /** jdk8 lambda用法  */
    @Test
    public void Page2() {
        Page<TCity> page = PageHelper.startPage(1, 10).doSelectPage(()-> {
            Example example = new Example(TCity.class);
            example.orderBy("id").desc().orderBy("cityName").orderBy("description").asc();
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("cityName", "鹰潭");
            mapper.selectByExample(example);
        });
        System.out.println(JSON.toJSONString(page));
    }
}
