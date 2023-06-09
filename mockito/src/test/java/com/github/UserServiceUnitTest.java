package com.github;

import com.github.domain.service.FlowerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

/*
    Необходимо замокать nameService и протестировать userService.getUserName
    TODO
        Использовать аннотацию запуска проекта с использованием SpringJUnit4ClassRunner
        Использовать аннотацию SpringBootTest вместе с MocksApplication.class
        Добавить ссылка на UserService
        Добавить ссылку на NameService
        Написать функцию для тестирования userService которая мокает nameService и возвращает "Mock user name"
*/
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MocksApplication.class)
public class UserServiceUnitTest {
    @Autowired
    private UserService userService;
    @Autowired
    private NameService nameService;

    @Test
    @Order(1)
    public void CreateUserTest() {
        when(nameService.getUserName("check")).thenReturn("Mock user name");
        String retUsername = userService.getUserName("check");
        Assert.assertEquals(retUsername, "Mock user name");
    }
}