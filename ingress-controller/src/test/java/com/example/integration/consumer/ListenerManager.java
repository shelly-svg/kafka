package com.example.integration.consumer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class ListenerManager {

    private static final int POOL_SIZE = 20;

    private final List<KafkaListener> kafkaListeners;
    private final ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

    @PostConstruct
    private void startListeners() {
        kafkaListeners.forEach(this::start);
    }

    public void start(KafkaListener kafkaListener) {
        executorService.submit(kafkaListener::start);
    }

    @PreDestroy
    private void stopListeners() {
        executorService.shutdownNow();
    }

}