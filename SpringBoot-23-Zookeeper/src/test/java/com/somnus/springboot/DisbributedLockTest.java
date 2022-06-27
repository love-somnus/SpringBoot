package com.somnus.springboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author kevin.liu
 * @title: DisbributedLockTest
 * @projectName SpringBoot
 * @description: TODO
 * @date 2021/12/15 11:19
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DisbributedLockTest {

    @Autowired
    private ZookeeperLockRegistry zookeeperLockRegistry;

    @Test
    public void lock(){
        Lock lock = zookeeperLockRegistry.obtain("zookeeper");
        try{
            //尝试在指定时间内加锁，如果已经有其他锁锁住，获取当前线程不能加锁，则返回false，加锁失败；加锁成功则返回true
            if(lock.tryLock(3, TimeUnit.SECONDS)){
                log.info("lock is ready");
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException e) {
            log.error("obtain lock error",e);
        } finally {
            lock.unlock();
        }

    }
}
