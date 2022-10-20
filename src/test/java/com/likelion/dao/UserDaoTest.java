package com.likelion.dao;


import com.likelion.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    UserDao userDao;
    @Autowired
    ApplicationContext context;

    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("localUserDao", UserDao.class);
    }

    @Test
    void findById(){
        assertThrows(EmptyResultDataAccessException.class,()->{
            userDao.findById("14");
        });
    }
    @Test
    @DisplayName("delete ok?")
    void addAndGet() throws SQLException, ClassNotFoundException {
        //UserDao userDao = new UserDao();
        UserDao userDao = context.getBean("localUserDao",UserDao.class);
//
//        userDao.deleteAll();
//        assertEquals(0,userDao.getCount());

        User user = new User("1","Guni","password");
        userDao.add(user);

        User selectedUser = userDao.findById("1");
        assertEquals("Guni",selectedUser.getName());
        assertEquals("password",selectedUser.getPassword());
    }
}