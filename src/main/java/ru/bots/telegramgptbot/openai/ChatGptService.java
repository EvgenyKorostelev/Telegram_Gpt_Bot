package ru.bots.telegramgptbot.openai;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bots.telegramgptbot.openai.api.ChatCompletionRequest;
import ru.bots.telegramgptbot.openai.api.Message;
import ru.bots.telegramgptbot.openai.api.OpenAiClient;

@Service
@AllArgsConstructor
public class ChatGptService {

    private final OpenAiClient openAiClient;
    private final ChatGptHistoryService chatGptHistoryService;


    @Nonnull
    public String getResponseChatForUser(Long userId, String userTextInput) {

        chatGptHistoryService.createHistoryIfNotExist(userId);

        var history = chatGptHistoryService.addMessageToHistory(userId, Message.builder()
                .content(userTextInput)
                .role("user")
                .build());

        var request = ChatCompletionRequest.builder()
                .model("gpt-4")
                .messages(history.chatMessages())
                .build();
        var response = openAiClient.createChatCompletion(request);

        var messageFromGpt = response.choices().get(0).message();

        chatGptHistoryService.addMessageToHistory(userId, messageFromGpt);

        return messageFromGpt.content();
    }
}
