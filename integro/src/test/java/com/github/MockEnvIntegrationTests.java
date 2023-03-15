package com.github;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;

import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class MockEnvIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateClient exchangeRateClient;


    @Test
    void createOrder() throws Exception {
        // TODO: протестируйте успешное создание заказа на 100 евро
        mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":  \"EUR 100\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @Sql("/unpaid-order.sql")
    void payOrder() throws Exception {
        mockMvc.perform(post("/order/1/pay").contentType(MediaType.APPLICATION_JSON)
                .content("{\"creditCardNumber\":  \"4532756279624064\"}"));
    }

    @Test
    @Sql("/paid-order.sql")
    void getReceipt() throws Exception {
        CurrencyUnit usd = Monetary.getCurrency("USD");
        CurrencyUnit eur = Monetary.getCurrency("EUR");

        when(exchangeRateClient.getExchangeRate(eur, usd)).thenReturn(BigDecimal.valueOf(100));

        mockMvc.perform(get("/order/1/receipt").param("currency", usd.toString()))
                .andExpect(status().isOk());
    }
}
