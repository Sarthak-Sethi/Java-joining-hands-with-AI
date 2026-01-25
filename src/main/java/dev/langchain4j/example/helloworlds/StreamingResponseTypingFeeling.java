package dev.langchain4j.example.helloworlds;

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;

import java.util.concurrent.CompletableFuture;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class StreamingResponseTypingFeeling {

    public static void main(String[] args) {

        OpenAiStreamingChatModel model = OpenAiStreamingChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY)
                .modelName(GPT_4_O_MINI)
                .build();

        var myCustomRunningModel = OllamaStreamingChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("java-brain") // <--- MUST MATCH the name you used in 'ollama create'
                .temperature(0.7)        // 0.0 = Precise answers (good for coding/logic)
                .build();

        String prompt = "Write a short funny poem about developers vs doctors, 10 lines maximum";

        System.out.println("Nr of chars: " + prompt.length());
        System.out.println("Nr of tokens: " + new OpenAiTokenCountEstimator(GPT_4_O_MINI).estimateTokenCountInText(prompt));

        CompletableFuture<ChatResponse> futureChatResponse = new CompletableFuture<>();

        myCustomRunningModel.chat(prompt, new StreamingChatResponseHandler() {

            @Override
            public void onPartialResponse(String partialResponse) {
                System.out.print(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                System.out.println("\n\nDone streaming");
                futureChatResponse.complete(completeResponse);
            }

            @Override
            public void onError(Throwable error) {
                futureChatResponse.completeExceptionally(error);
            }
        });

        futureChatResponse.join();
    }
}
