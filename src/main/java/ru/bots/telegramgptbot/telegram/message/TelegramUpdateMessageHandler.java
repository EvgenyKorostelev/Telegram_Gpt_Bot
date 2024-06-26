package ru.bots.telegramgptbot.telegram.message;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bots.telegramgptbot.command.TelegramCommandsDispatcher;
import ru.bots.telegramgptbot.telegram.TelegramAsyncMessageSender;

@Slf4j
@Service
@AllArgsConstructor
public class TelegramUpdateMessageHandler {

    private final TelegramCommandsDispatcher telegramCommandsDispatcher;
    private final TelegramAsyncMessageSender telegramAsyncMessageSender;
    private final TelegramTextHandler telegramTextHandler;
    private final TelegramVoiceHandler telegramVoiceHandler;

    public BotApiMethod<?> handleMessage(Message message){
        if(telegramCommandsDispatcher.isCommand(message)){
            return (telegramCommandsDispatcher.processCommand(message));
        }
        var chatId = message.getChatId().toString();
        if(message.hasVoice() || message.hasText()){
            telegramAsyncMessageSender.sendMessageAsync(chatId,
                    () -> handleMessageAsync(message),
                    throwable -> getErrorMessage(throwable, chatId)
                    );
        }
        return null;
    }

    private SendMessage handleMessageAsync(Message message) {
        if(message.hasVoice()){
            return telegramVoiceHandler.processVoice(message);
        } else {
            return telegramTextHandler.processTextMessage(message);
        }
    }

    private SendMessage getErrorMessage(Throwable throwable, String chatId) {
            log.error("Произошла ошибка", throwable);
            return SendMessage.builder()
                    .chatId(chatId)
                    .text("Произошла ошибка попробуйте позже . . .")
                    .build();
    }
}
