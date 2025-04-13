package org.abigotado.otp_service.features.otp.delivery.spi;

public interface DeliveryChannel {
    void send(String code, String recipient);
}