package com.railsware;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EmailClient {

    private static final String API_URL = "https://send.api.mailtrap.io/api/send";
    private final String apiToken;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public EmailClient(String apiToken) {
        this.apiToken = apiToken;
        this.client = new OkHttpClient().newBuilder().build();
        this.objectMapper = new ObjectMapper();
    }

    public void send(String senderName, String senderEmail, String recipientName, String recipientEmail, String subject, String text, String html, List<Map<String, String>> attachments) throws IOException {
        validateEmail(senderEmail);
        validateEmail(recipientEmail);

        Map<String, Object> email = Map.of(
                "from", Map.of("email", senderEmail, "name", senderName),
                "to", List.of(Map.of("email", recipientEmail, "name", recipientName)),
                "subject", subject,
                "text", text,
                "html", html,
                "attachments", attachments
        );

        String json = objectMapper.writeValueAsString(email);
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to send email: " + response.message());
            }
        }
    }

    private void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }
}
