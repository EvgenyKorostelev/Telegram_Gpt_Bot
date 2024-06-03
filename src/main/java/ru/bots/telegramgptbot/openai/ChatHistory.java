package ru.bots.telegramgptbot.openai;

import lombok.Builder;
import ru.bots.telegramgptbot.openai.api.Message;

import java.util.List;
@Builder
public record ChatHistory(List<Message> chatMessages) {

}
