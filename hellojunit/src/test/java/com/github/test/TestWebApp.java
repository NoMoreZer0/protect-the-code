package com.github.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jdk.jfr.Name;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/*
	TODO
	Добавить WebApplicationContext для тестов
	Добавить MockMvc
	Создать метод с аннотацией @Before которая создает mockMvc на основе webApplicationContext
	Написать метод тестирования метода /employee
		Должен проверяться HTTP статус ответа
		Должен проверять contentType ответа
		Должно проверять значение поля "name"
		Должно проверять значение поля "designation"
		Должно проверять значение поля "salary"
		Должно проверять значение поля "empId"
*/
public class TestWebApp extends SpringBootHelloWorldTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public TestWebApp() {

    }

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Order(1)
    @DisplayName("Json have correct number of arguments")
    public void testNumberOfArguments() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(4)));
    }

    @Test
    @Order(2)
    @DisplayName("Employee has correct name")
    public void testEmployeeName() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("emp1")));
    }

    @Test
    @Order(3)
    @DisplayName("Employee has correct designation")
    public void testEmployeeDesignation() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation", Matchers.is("manager")));
    }

    @Test
    @Order(4)
    @DisplayName("Employee has correct ID")
    public void testEmployeeID() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empId", Matchers.is("1")));
    }

    @Test
    @Order(5)
    @DisplayName("Employee has correct salary")
    public void testEmployeeSalary() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salary", Matchers.is(3000.0)));
    }
}
