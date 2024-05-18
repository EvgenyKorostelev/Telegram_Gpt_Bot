package ru.bots.telegramgptbot.openai;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bots.telegramgptbot.openai.api.CreateTranslationRequest;
import ru.bots.telegramgptbot.openai.api.OpenAiClient;

@Service
@AllArgsConstructor
public class TranscribeVoiceToTextService {

    private final OpenAiClient openAiClient;

    public String transcribe(java.io.File audioFile) {
        var response = openAiClient.createTranslation(CreateTranslationRequest.builder()
                .audioFile(audioFile)
                .model("whisper-1")
                .build());
        return response.text();
    }
}
