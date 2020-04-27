package com.aot.payments.model;

public enum InvoiceStatus {
    INVOICED("Invoiced"),
    COMPLETED("Payment Completed"),
    PARTIAL("Partial Payment Completed");

    String status;
    InvoiceStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
