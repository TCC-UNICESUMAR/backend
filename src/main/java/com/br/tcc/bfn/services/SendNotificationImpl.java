package com.br.tcc.bfn.services;


import com.br.tcc.bfn.models.TemplateBodySms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SendNotificationImpl implements SendNotification{

    public static final String TWILIO_SID = "twilio.sid";
    public static final String TWILIO_KEY = "twilio.key";
    public static final String TWILIO_PHONE_FROM = "twilio.phone.from";
    @Autowired
    private Environment environment;

    @Override
    public void send(String to, TemplateBodySms templateBodySms, Long donationOrderId) {
        Twilio.init(environment.getProperty(TWILIO_SID), environment.getProperty(TWILIO_KEY));

        Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(environment.getProperty(TWILIO_PHONE_FROM)),
                        templateBodySms.getMessage())
                .create();

    }

}
