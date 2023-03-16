package com.github.app.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.app.api.MessageApi;
import com.github.domain.model.Message;
import com.github.domain.service.MessageService;
import com.github.domain.util.MessageMatcher;

/*
    Необходимо протестировать MessageController используя моки
    TODO
        создайте мок для MessageService
        создайте мок для MessageController
        протестируйте создание нового сообщения
*/
@RunWith(MockitoJUnitRunner.class)
public class MessageControllerUnitTest {
    @Mock
    MessageController messageController;

    @Test
    @Order(1)
    public void createMessageTest() {
        MessageApi messageApi = new MessageApi();
        messageApi.setFrom("1");
        messageApi.setTo("2");
        messageApi.setText("3");
        Message message = new Message();
        message.setFrom("4");
        message.setTo("5");
        message.setText("6");
        when(messageController.createMessage(messageApi)).thenReturn(message);
        Message retMessage = messageController.createMessage(messageApi);
        Assert.assertEquals(retMessage, message);
    }
}