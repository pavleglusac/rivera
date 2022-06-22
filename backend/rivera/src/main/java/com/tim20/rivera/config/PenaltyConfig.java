package com.tim20.rivera.config;

import com.tim20.rivera.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
public class PenaltyConfig {

    private final ClientService clientService;

    @Autowired
    public PenaltyConfig(ClientService clientService) {
        this.clientService = clientService;
    }

    @Scheduled(cron = "0 0 0 1 * *")
    private void deletePenalties() {
        clientService.deletePenalties();
        System.out.println("All clients penalties has been deleted.");
    }
}
