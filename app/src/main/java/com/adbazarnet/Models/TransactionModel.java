package com.adbazarnet.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("transaction_number")
    @Expose
    private String transactionNumber;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("is_modified")
    @Expose
    private Boolean isModified;
    @SerializedName("user")
    @Expose
    private Integer user;
    @SerializedName("package")
    @Expose
    private Integer _package;
    @SerializedName("payment_method")
    @Expose
    private Integer paymentMethod;

    public TransactionModel(String transactionNumber, String transactionId, String contactNumber, Integer _package, Integer paymentMethod) {
        this.transactionNumber = transactionNumber;
        this.transactionId = transactionId;
        this.contactNumber = contactNumber;
        this._package = _package;
        this.paymentMethod = paymentMethod;
    }

    public TransactionModel() {
    }

    public Integer getId() {
        return id;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated() {
        return created;
    }

    public Boolean getModified() {
        return isModified;
    }

    public Integer getUser() {
        return user;
    }

    public Integer get_package() {
        return _package;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }
}
