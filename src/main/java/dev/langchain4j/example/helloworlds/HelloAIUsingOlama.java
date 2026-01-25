package dev.langchain4j.example.helloworlds;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.util.List;

public class HelloAIUsingOlama {

    public static void main(String[] args) {
//
//        ChatModel model = OpenAiChatModel.builder()
//                .apiKey(ApiKeys.OPENAI_API_KEY)
//                .modelName(GPT_4_O_MINI)
//                .build();

        var myCustomRunningModel = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("java-brain")
                .temperature(0.7)        // 0.0 = Precise answers
                .build();


        var answer = myCustomRunningModel.chat(List.of(UserMessage.from("hello AI")));

        System.out.println(answer);
    }
}
