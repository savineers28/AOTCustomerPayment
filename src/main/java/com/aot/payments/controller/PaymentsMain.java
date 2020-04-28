package com.aot.payments.controller;

import com.aot.payments.service.PaymentProcessService;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.aot.payments.topic.InvoiceReceiver.getInvoiceFromTopic;

public class PaymentsMain {
    public static void main(String[] args) throws IOException {
        System.out.println("Inside Payments Main");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println("Getting Invoice From Topic");
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
        System.out.println("Producing Payment Process Service");
        new PaymentProcessService().showOptions();
    }
}
