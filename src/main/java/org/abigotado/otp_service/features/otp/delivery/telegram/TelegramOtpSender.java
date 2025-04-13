package org.abigotado.otp_service.features.otp.delivery.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abigotado.otp_service.features.otp.delivery.spi.DeliveryChannel;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramOtpSender implements DeliveryChannel {

    private final TelegramProperties properties;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void send(String code, String recipient) {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", properties.getToken());

        String message = "\uD83D\uDD10 Ваш OTP-код: %s".formatted(code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = String.format(
            "{\"chat_id\":\"%s\", \"text\":\"%s\"}",
            recipient, message
        );

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("✅ Telegram OTP отправлен успешно");
            } else {
                log.error("❌ Ошибка при отправке Telegram OTP: {}", response.getBody());
            }
        } catch (Exception e) {
            log.error("❌ Ошибка при отправке через Telegram", e);
        }
    }
}