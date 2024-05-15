package com.railsware;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailClientTest {

    private static String recipientEmail;
    private static String senderEmail;
    private static String senderName;
    private static String recipientName;
    private static String subject;
    private static String textContent;
    private static String htmlContent;
    private static String attachmentFilename;
    private static String attachmentContent;
    private static String attachmentType;
    private static String invalidEmail;
    private static EmailClient emailClient;

    @BeforeAll
    public static void setup() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = EmailClientTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find test.properties");
                return;
            }
            properties.load(input);
        }

        recipientEmail = properties.getProperty("recipient.email");
        senderName = properties.getProperty("sender.name");
        recipientName = properties.getProperty("recipient.name");
        attachmentType = properties.getProperty("attachment.type");
        senderEmail = properties.getProperty("sender.email");
        subject = properties.getProperty("subject");
        textContent = properties.getProperty("text.content");
        htmlContent = properties.getProperty("html.content");
        attachmentFilename = properties.getProperty("attachment.filename");
        attachmentContent = properties.getProperty("attachment.content");
        invalidEmail = properties.getProperty("invalid.email");
        invalidEmail = properties.getProperty("invalid.recipient.email");
        emailClient = new EmailClient(properties.getProperty("api.token"));
    }

    @Test
    @DisplayName("Test sending email with text and HTML content")
    public void testSendEmailWithTextAndHtml() {
        assertDoesNotThrow(() -> emailClient.send(
                senderName,
                senderEmail,
                recipientName,
                recipientEmail,
                subject,
                textContent,
                htmlContent,
                List.of()
        ));
    }

    @Test
    @DisplayName("Test sending email with attachments")
    public void testSendEmailWithAttachments() {
        List<Map<String, String>> attachments = List.of(
                Map.of("filename", attachmentFilename, "content", attachmentContent, "type", attachmentType)
        );
        assertDoesNotThrow(() -> emailClient.send(
                senderName,
                senderEmail,
                recipientName,
                recipientEmail,
                subject,
                textContent,
                htmlContent,
                attachments
        ));
    }

    @Test
    @DisplayName("Test sending email with invalid sender email")
    public void testSendEmailWithInvalidSenderEmail() {
        assertThrows(IllegalArgumentException.class, () -> emailClient.send(
                senderName,
                invalidEmail,
                recipientName,
                recipientEmail,
                subject,
                textContent,
                htmlContent,
                List.of()
        ));
    }

    @Test
    @DisplayName("Test sending email with invalid recipient email")
    public void testSendEmailWithInvalidRecipientEmail() {
        assertThrows(IllegalArgumentException.class, () -> emailClient.send(
                senderName,
                senderEmail,
                recipientName,
                invalidEmail,
                subject,
                textContent,
                htmlContent,
                List.of()
        ));
    }

    @Test
    @DisplayName("Test sending email with empty HTML content")
    public void testSendEmailWithEmptyHtml() {
        assertThrows(NullPointerException.class, () -> emailClient.send(
                senderName,
                senderEmail,
                recipientName,
                recipientEmail,
                subject,
                textContent,
                null,
                List.of()
        ));
    }

    @Test
    @DisplayName("Test sending email with empty text content")
    public void testSendEmailWithEmptyText() {
        assertThrows(NullPointerException.class, () -> emailClient.send(
                senderName,
                senderEmail,
                recipientName,
                recipientEmail,
                subject,
                null,
                htmlContent,
                List.of()
        ));
    }
}
