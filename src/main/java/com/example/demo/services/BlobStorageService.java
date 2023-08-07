package com.example.demo.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlobStorageService {

    @Autowired
    private AzureConfigurationReader configuration;

    private BlobContainerClient blobContainerClient;

    public List<String> listBlobs() {
        List<String> blobNames = new ArrayList<>();
        BlobContainerClient containerClient = getBlobContainerClient();
        for (BlobItem blobItem : containerClient.listBlobs()) {
            blobNames.add(blobItem.getName());
        }
        return blobNames;
    }

    public void uploadBlob(String blobName, MultipartFile file) throws IOException {
        BlobClient blobClient = getBlobClient(blobName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);
    }

    public InputStream downloadBlob(String blobName) {
        if(blobName != null && !blobName.isEmpty()) {
            BlobClient blobClient = getBlobClient(blobName);
            if (blobClient.exists()) {
                return blobClient.downloadContent().toStream();
            }
        }
        return null;
    }

    public boolean deleteBlob(String blobName) {
        BlobClient blobClient = getBlobClient(blobName);

        if (blobClient.exists()) {
            blobClient.delete();
            return true;
        }
        return false;
    }

    private BlobContainerClient getBlobContainerClient() {
        if (blobContainerClient == null) {
            return new BlobContainerClientBuilder()
                    .connectionString(configuration.getAzureStorageConnectionString())
                    .containerName(configuration.getAzureStorageContainerName())
                    .buildClient();
        }
        return this.blobContainerClient;
    }

    private BlobClient getBlobClient(String blobName) {
        BlobContainerClient containerClient = getBlobContainerClient();
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        return blobClient;
    }
}
