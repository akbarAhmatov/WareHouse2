package com.example.WareHouse2.bot;

import com.example.WareHouse2.service.CategoryService;
import com.example.WareHouse2.service.ProductService;
import com.example.WareHouse2.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class WarehouseTelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    String contacts = """
            Phone: +998 95 426 72 76
            Email: akbarahmatov86@gmail.com
            """;

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final ProductService productService;
    private final CategoryService categoryService;
    private final WarehouseService warehouseService;

    public WarehouseTelegramBot(ProductService productService,
                                CategoryService categoryService,
                                WarehouseService warehouseService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.warehouseService = warehouseService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getFrom().getFirstName();

            log.info("Received message: {} from user: {} (chatId: {})", messageText, userName, chatId);

            String response = handleCommand(messageText, userName);
            sendMessage(chatId, response);
        }
    }

    private String handleCommand(String command, String userName) {
        command = command.trim().toLowerCase();

        switch (command) {
            case "/start":
                return "👋 Привет, " + userName + "!\n\n" +
                        "🏭 Добро пожаловать в Warehouse Management Bot!\n\n" +
                        "📋 Доступные команды:\n" +
                        "/products - Список всех продуктов\n" +
                        "/categories - Список категорий\n" +
                        "/warehouses - Список складов\n" +
                        "/stats - Статистика\n" +
                        "/help - Помощь";

            case "/help":
                return "📖 *Помощь* \n"+ contacts +"\n\n" +
                        "Используйте следующие команды:\n\n" +
                        "📦 /products - Показать все продукты\n" +
                        "📂 /categories - Показать категории\n" +
                        "🏢 /warehouses - Показать склады\n" +
                        "📊 /stats - Общая статистика\n" +
                        "❓ /help - Эта справка";

            case "/products":
                return getProductsList();

            case "/categories":
                return getCategoriesList();

            case "/warehouses":
                return getWarehousesList();

            case "/stats":
                return getStatistics();

            default:
                return "❌ Неизвестная команда!\n\n" +
                        "Используйте /help для списка доступных команд.";
        }
    }

    private String getProductsList() {
        var products = productService.findAll();
        
        if (products.isEmpty()) {
            return "📦 *Продукты*\n\nСписок продуктов пуст.";
        }

        StringBuilder response = new StringBuilder("📦 *Список продуктов:*\n\n");
        for (var product : products) {
            response.append("▫️ ").append(product.getName())
                    .append("\n   SKU: ").append(product.getSku())
                    .append("\n   Цена: $").append(product.getPrice())
                    .append("\n   Количество: ").append(product.getQuantity())
                    .append("\n   Категория: ").append(product.getCategoryName() != null ? product.getCategoryName() : "N/A")
                    .append("\n   Склад: ").append(product.getWarehouseName() != null ? product.getWarehouseName() : "N/A")
                    .append("\n\n");
        }
        
        response.append("Всего продуктов: ").append(products.size());
        return response.toString();
    }

    private String getCategoriesList() {
        var categories = categoryService.findAll();
        
        if (categories.isEmpty()) {
            return "📂 *Категории*\n\nСписок категорий пуст.";
        }

        StringBuilder response = new StringBuilder("📂 *Список категорий:*\n\n");
        for (var category : categories) {
            response.append("▫️ ").append(category.getName())
                    .append(" (").append(category.getCode()).append(")")
                    .append("\n   ").append(category.getDescription() != null ? category.getDescription() : "Без описания")
                    .append("\n\n");
        }
        
        response.append("Всего категорий: ").append(categories.size());
        return response.toString();
    }

    private String getWarehousesList() {
        var warehouses = warehouseService.findAll();
        
        if (warehouses.isEmpty()) {
            return "🏢 *Склады*\n\nСписок складов пуст.";
        }

        StringBuilder response = new StringBuilder("🏢 *Список складов:*\n\n");
        for (var warehouse : warehouses) {
            response.append("▫️ ").append(warehouse.getName())
                    .append("\n   Локация: ").append(warehouse.getLocation())
                    .append("\n   Вместимость: ").append(warehouse.getCapacity())
                    .append("\n   Менеджер: ").append(warehouse.getManager() != null ? warehouse.getManager() : "N/A")
                    .append("\n\n");
        }
        
        response.append("Всего складов: ").append(warehouses.size());
        return response.toString();
    }

    private String getStatistics() {
        long productCount = productService.count();
        long categoryCount = categoryService.count();
        long warehouseCount = warehouseService.count();

        return "📊 *Статистика системы*\n\n" +
                "📦 Продуктов: " + productCount + "\n" +
                "📂 Категорий: " + categoryCount + "\n" +
                "🏢 Складов: " + warehouseCount + "\n\n" +
                "✅ Система работает нормально!";
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.enableMarkdown(true);

        try {
            execute(message);
            log.info("Message sent to chatId: {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Error sending message to chatId: {}", chatId, e);
        }
    }
}