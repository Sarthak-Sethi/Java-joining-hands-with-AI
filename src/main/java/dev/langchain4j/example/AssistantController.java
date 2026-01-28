package dev.langchain4j.example.aiservice;

import dev.langchain4j.service.spring.AiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

/**
 * This is an example of using an {@link AiService}, a high-level LangChain4j API.
 */
@RestController
public class AssistantController {

    private final Assistant assistant;
    private final StreamingAssistant streamingAssistant;
   // private final ChatClient chatClient;
    private final ChatClient customChatCLientUsingOurVector;

    public AssistantController(Assistant assistant, StreamingAssistant streamingAssistant, ChatClient.Builder builder, VectorStore vectorStore) {
        this.assistant = assistant;
        this.streamingAssistant = streamingAssistant;
      //  this.chatClient = builder.build();
        this.customChatCLientUsingOurVector = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();;
    }

    @GetMapping("/assistant")
    public String assistant(@RequestParam(value = "message", defaultValue = "What is the current time?") String message) {
        return assistant.chat(message);
    }

    @GetMapping("/generateCode")
    public String generateCode(@RequestParam(value = "message", defaultValue = "How can i get set rejected reason in claimdto ?") String message) {
        String userDtoSource = CodeReader.readClass(ClaimDto.class);
        String stringUtilSource = CodeReader.readClass(ClaimProcessingUtil.class);
        return assistant.generateCode(message,userDtoSource,stringUtilSource);
    }

    @GetMapping(value = "/streamingAssistant", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamingAssistant(
            @RequestParam(value = "message", defaultValue = "What is the current time?") String message) {
        return streamingAssistant.chat(message);
    }

    @GetMapping(value = "/vector")
    public String vector(
            @RequestParam(value = "message", defaultValue = "what do we know about hippa voilation ") String message) {
        return customChatCLientUsingOurVector.prompt()
                .user(message)
                .call()
                .content();
    }


}
