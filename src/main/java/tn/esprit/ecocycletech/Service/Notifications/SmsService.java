package tn.esprit.ecocycletech.Service.Notifications;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromNumber;

    /**
     * Initialize the Twilio client as soon as this bean is constructed.
     */
    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    /**
     * Send a one-off SMS.
     */
    public void sendSms(String to, String body) {
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(fromNumber),
                body
        ).create();
    }
}
