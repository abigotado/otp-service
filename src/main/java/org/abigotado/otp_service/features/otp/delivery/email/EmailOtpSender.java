package org.abigotado.otp_service.features.otp.delivery.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abigotado.otp_service.features.otp.delivery.spi.DeliveryChannel;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component("email")
@RequiredArgsConstructor
public class EmailOtpSender implements DeliveryChannel {

    private final JavaMailSender mailSender;
    private final EmailSenderProperties props;

    @Override
    public void send(String code, String recipient) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
            helper.setSubject("OTP Code");
            helper.setFrom(props.getFrom());
            helper.setText("Your code: %s".formatted(code));
            mailSender.send(message);
            log.info("Email sent to {}", recipient);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
        }
    }
}