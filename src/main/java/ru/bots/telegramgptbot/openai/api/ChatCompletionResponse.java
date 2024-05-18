package ru.bots.telegramgptbot.openai.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record ChatCompletionResponse(@JsonProperty("choices") List<Choice> choices) {

}
