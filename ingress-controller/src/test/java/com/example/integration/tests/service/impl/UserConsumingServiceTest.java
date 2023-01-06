package com.example.integration.tests.service.impl;

import com.example.constant.TestConstants;
import com.example.integration.config.TestConfig;
import com.example.integration.consumer.KafkaListener;
import com.example.integration.extension.KafkaExtension;
import com.example.reader.JsonFileReader;
import com.example.service.ConsumingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Import(TestConfig.class)
@ExtendWith(KafkaExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest
class UserConsumingServiceTest {

    @Autowired
    private ConsumingService userConsumingService;
    @Autowired
    private KafkaListener kafkaListener;
    @Autowired
    private JsonFileReader fileReader;

    @Test
    void shouldSendUserEntityToTargetTopic_whenReceivedEntityFromController() {
        // GIVEN
        var user = fileReader.read(TestConstants.PATH_TO_USER);

        // WHEN
        userConsumingService.doConsume(user);
        await().atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(300))
            .until(() -> kafkaListener.getPayloads().size() == 1);
        var consumedUser = kafkaListener.getPayloads().poll();

        // THEN
        assertThat(consumedUser).isEqualTo(user);
    }

}