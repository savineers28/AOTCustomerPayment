package com.aot.payments.service;

import com.aot.payments.model.Invoice;
import com.aot.payments.properties.PaymentConstants;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.aot.payments.dto.LineToObjectConverter.convertObjectToLine;
import static com.aot.payments.dto.LineToObjectConverter.convertJsonToInvoiceObject;

public class PaymentFileService {

    public void triggerInvoiceEvent(ConsumerRecords<String, String> consumerRecords) {
        consumerRecords.forEach(record -> {
            Invoice invoice = convertJsonToInvoiceObject(record.value(), new Invoice());
            System.out.println("Invoice received.");
            processInbound(invoice.getInvoiceNumber(), convertObjectToLine(invoice));
            System.out.println("Outbound file created.");
        });
    }

    public void processInbound(String invoiceNumber, String line) {
        String inboundFile = PaymentConstants.paymentInboundPath + "//" + invoiceNumber + ".txt";
        boolean fileExists = new File(inboundFile).exists();
        if(!fileExists) {
            File f = new File(inboundFile);
            try (FileWriter fileWriter = new FileWriter(inboundFile)) {
                fileWriter.write(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(inboundFile)) {
                fileWriter.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
