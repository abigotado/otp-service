package org.abigotado.otp_service.features.otp.delivery.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abigotado.otp_service.features.otp.delivery.spi.DeliveryChannel;
import org.smpp.Data;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.SubmitSM;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsOtpSender implements DeliveryChannel {
    private final SmsSenderProperties smsProps;

    @Override
    public void send(String code, String recipient) {
        try {
            TCPIPConnection connection = new TCPIPConnection(smsProps.getHost(), smsProps.getPort());
            Session session = new Session(connection);

            BindTransmitter request = new BindTransmitter();
            request.setSystemId(smsProps.getSystemId());
            request.setPassword(smsProps.getPassword());
            request.setSystemType(smsProps.getSystemType());
            request.setInterfaceVersion((byte) 0x34);
            request.setAddressRange(smsProps.getSourceAddr());

            BindResponse bindResponse = session.bind(request);
            if (bindResponse.getCommandStatus() != Data.ESME_ROK) {
                throw new RuntimeException("SMPP bind failed: %d".formatted(bindResponse.getCommandStatus()));
            }

            SubmitSM submitSM = new SubmitSM();
            submitSM.setSourceAddr(smsProps.getSourceAddr());
            submitSM.setDestAddr(recipient);
            submitSM.setShortMessage(code);

            session.submit(submitSM);
            log.info("SMPP: OTP message successfully sent to {}", recipient);

            session.unbind();
            session.close();

        } catch (Exception e) {
            log.error("SMPP: Error sending OTP to {}: {}", recipient, e.getMessage(), e);
        }
    }
}