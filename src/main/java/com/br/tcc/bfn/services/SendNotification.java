package com.br.tcc.bfn.services;

import com.br.tcc.bfn.models.TemplateBodySms;

public interface SendNotification {

    public void send(String to, TemplateBodySms templateBodySms, Long donationOrderId);
}
