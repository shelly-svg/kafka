package com.example.integration.tests.controller;

import com.example.constant.TestConstants;
import com.example.integration.config.TestConfig;
import com.example.integration.extension.KafkaExtension;
import com.example.reader.JsonFileReader;
import com.example.service.ConsumingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
@ExtendWith(KafkaExtension.class)
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest
class UserControllerTest {

    private static final String ENDPOINT = "/users";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonFileReader fileReader;
    @MockBean
    private ConsumingService userConsumingService;

    @Test
    void doConsume_shouldReturn200_whenUserWasSuccessfullyConsumed() throws Exception {
        // GIVEN
        var user = fileReader.read(TestConstants.PATH_TO_USER);

        // WHEN
        mockMvc.perform(post(ENDPOINT)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(user.toString()))

            // THEN
            .andExpect(status().isOk());

        then(userConsumingService).should().doConsume(user);
    }

    @Test
    void doConsume_shouldReturn405_whenEndpointCalledWithWrongHttpMethod() throws Exception {
        // WHEN
        mockMvc.perform(get(ENDPOINT))

            // THEN
            .andExpect(status().isMethodNotAllowed());
    }

}