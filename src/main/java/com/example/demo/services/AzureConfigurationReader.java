package com.example.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AzureConfigurationReader {

    @Value("${azure.storage.connectionString}")
    private String azureStorageConnectionString;

    @Value("${azure.storage.containerName}")
    private String azureStorageContainerName;

    @Value("${azure.emailCommunication.senderEmail}")
    private String senderEmailAddress;

    @Value("${azure.emailCommunication.connectionString}")
    private String emailServiceConnectionString;

    public String getAzureStorageConnectionString() {
        return azureStorageConnectionString;
    }

    public void setAzureStorageConnectionString(String azureStorageConnectionString) {
        this.azureStorageConnectionString = azureStorageConnectionString;
    }

    public String getAzureStorageContainerName() {
        return azureStorageContainerName;
    }

    public String getSenderEmailAddress() {
        return senderEmailAddress;
    }

    public String getEmailServiceConnectionString() {
        return emailServiceConnectionString;
    }
}