package org.abigotado.otp_service.features.otp.delivery.file;

import lombok.extern.slf4j.Slf4j;
import org.abigotado.otp_service.features.otp.delivery.spi.DeliveryChannel;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@Component
public class FileOtpSender implements DeliveryChannel {

    private static final String FILE_NAME = "otp-code.txt";

    @Override
    public void send(String otp, String ignored) {
        try {
            Path filePath = Path.of(FILE_NAME);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
                writer.write("Code: %s".formatted(otp));
                writer.newLine();
                writer.write("------------");
                writer.newLine();
            }
            log.info("OTP code saved to file: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to write OTP code to file", e);
        }
    }
}