package com.example.unit.service.impl;

import com.example.constant.TestConstants;
import com.example.reader.JsonFileReader;
import com.example.service.ConsumingService;
import com.example.service.impl.UserConsumingService;
import com.example.service.messaging.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class UserConsumingServiceTest {

    private final JsonFileReader fileReader = new JsonFileReader(new ObjectMapper());
    private final Producer producer = mock(Producer.class);
    private final ConsumingService consumingService = new UserConsumingService(producer);

    @Test
    void doConsume_shouldSendPayload_whenCalled() {
        // GIVEN
        var payload = fileReader.read(TestConstants.PATH_TO_USER);

        // WHEN
        consumingService.doConsume(payload);

        // THEN
        then(producer).should().doSend(any(), any(), eq(payload));
    }

}