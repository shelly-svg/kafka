package com.example.integration;

import com.example.constant.TestConstants;
import com.example.controller.UserController;
import com.example.integration.config.TestConfig;
import com.example.integration.consumer.KafkaListener;
import com.example.integration.extension.KafkaExtension;
import com.example.reader.JsonFileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Import(TestConfig.class)
@ExtendWith(KafkaExtension.class)
@SpringBootTest
class IngressControllerTest {

    @Autowired
    private UserController userController;
    @Autowired
    private KafkaListener kafkaListener;
    @Autowired
    private JsonFileReader fileReader;

    @Test
    void shouldSendUserEntityToTargetTopic_whenReceivedEntityFromController() {
        var user = fileReader.read(TestConstants.PATH_TO_USER);
        userController.consume(user);

        await().atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(300))
            .until(() -> kafkaListener.getPayloads().size() == 1);
        var consumedUser = kafkaListener.getPayloads().poll();

        assertThat(consumedUser).isEqualTo(user);
    }

}