package com.hz.service;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()

//关闭回滚，只要加上@Rollback(false)注解即可
@Rollback(false)

//单元个测试的时候如果不想造成垃圾数据，可以开启事物功能，记在方法或者类头部添加@Transactional注解即可
@Transactional
class DocServiceTest {

    @BeforeClass
    void before(){

    }

    @Test
    void createDocument() {
        System.out.println("创建");
    }

    @Test
    void deleteDocument() {
    }

    @Test
    void updateDocument() {
    }

    @Test
    void getDocument() {
    }

    @Test
    void getDocumentList() {
    }

    @Test
    void getDocsPage() {
    }
}