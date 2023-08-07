package com.example.demo.services;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailAddress;
import com.azure.communication.email.models.EmailAttachment;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.BinaryData;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private AzureConfigurationReader configuration;

    public void sendEmailWithAttachments() {
        EmailClient emailClient = new EmailClientBuilder()
                .connectionString(configuration.getEmailServiceConnectionString())
                .buildClient();

        BinaryData attachmentContent = BinaryData.fromFile(
                new File("C:/attachment.txt").toPath());
        EmailAttachment attachment = new EmailAttachment(
                "attachment.txt",
                "text/plain",
                attachmentContent
        );

        EmailMessage message = new EmailMessage()
                .setSenderAddress("<sender-email-address>")
                .setToRecipients("<recipient-email-address>")
                .setSubject("test subject")
                .setBodyPlainText("test message")
                .setAttachments(attachment);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(message);
        PollResponse<EmailSendResult> response = poller.waitForCompletion();

        System.out.println("Operation Id: " + response.getValue().getId());
    }

    public void sendEmailToMultipleRecipients(List<String> recipientEmails, String subject, String body) {
        EmailClient emailClient = new EmailClientBuilder()
                .connectionString(configuration.getEmailServiceConnectionString())
                .buildClient();

        if (recipientEmails == null || recipientEmails.isEmpty()) {
            return;
        }

        List<EmailAddress> emailAddresses = new ArrayList<>();
        for (String singleEmail : recipientEmails) {
            emailAddresses.add(new EmailAddress(singleEmail));
        }

        EmailMessage message = new EmailMessage()
                .setSenderAddress(configuration.getSenderEmailAddress())
                .setSubject(subject)
                .setBodyPlainText(body)
                .setToRecipients(emailAddresses);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(message);
        PollResponse<EmailSendResult> response = poller.waitForCompletion();

        System.out.println("Operation Id: " + response.getValue().getId());
    }

    public void sendEmailToSingleRecipient(String recipientEmail, String subject, String body) {
        EmailClient emailClient = new EmailClientBuilder()
                .connectionString(configuration.getEmailServiceConnectionString())
                .buildClient();

        EmailMessage message = new EmailMessage()
                .setSenderAddress(configuration.getSenderEmailAddress())
                .setToRecipients(recipientEmail)
                .setSubject(subject)
                .setBodyPlainText(body);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(message);
        PollResponse<EmailSendResult> response = poller.waitForCompletion();

        System.out.println("Operation Id: " + response.getValue().getId());
    }
}
