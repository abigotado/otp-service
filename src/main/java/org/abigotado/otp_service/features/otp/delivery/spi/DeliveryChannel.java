package org.abigotado.otp_service.features.otp.delivery.spi;

public interface DeliveryChannel {
    void send(String destination, String message);
}