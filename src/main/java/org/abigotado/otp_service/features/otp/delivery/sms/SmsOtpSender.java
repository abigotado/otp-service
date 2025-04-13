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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component("smsDeliveryChannel")
@RequiredArgsConstructor
public class SmsOtpSender implements DeliveryChannel {

    @Qualifier("smsSenderProperties")
    private final SmsSenderProperties smsProps;

    @Override
    public void send(String destination, String message) {
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
            submitSM.setDestAddr(destination);
            submitSM.setShortMessage(message);

            session.submit(submitSM);
            log.info("SMPP: OTP message successfully sent to {}", destination);

            session.unbind();
            session.close();

        } catch (Exception e) {
            log.error("SMPP: Error sending OTP to {}: {}", destination, e.getMessage(), e);
        }
    }
}