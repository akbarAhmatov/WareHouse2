package com.example.WareHouse2;

import com.example.WareHouse2.bot.WarehouseTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(WarehouseTelegramBot warehouseTelegramBot) {
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(warehouseTelegramBot);
            log.info("✅ Telegram Bot registered successfully: @{}", warehouseTelegramBot.getBotUsername());
        } catch (TelegramApiException e) {
            log.error("❌ Failed to register Telegram Bot", e);
        }
        return botsApi;
    }
}