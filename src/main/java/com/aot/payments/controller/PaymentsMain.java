package com.aot.payments.controller;

import com.aot.payments.service.PaymentProcessService;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.aot.payments.topic.InvoiceReceiver.getInvoiceFromTopic;

public class PaymentsMain {
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()-> {
            for(;;) {
                try {
                    getInvoiceFromTopic();
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        new PaymentProcessService().showOptions();
    }
}
