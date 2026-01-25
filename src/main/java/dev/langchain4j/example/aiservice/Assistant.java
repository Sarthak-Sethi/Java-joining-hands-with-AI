package dev.langchain4j.example.aiservice;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface Assistant {

    @SystemMessage("You are very rude assistant")
    String chat(String userMessage);

    @SystemMessage("""
        You are a Senior Java Developer.
        I will provide you with a Context including a Utility class and a DTO.
        Your task is to generate code that strictly uses the provided Utility methods and DTO structure.
        
        --- CONTEXT START ---
        
        [DTO Structure]:
        {{claimDto}}
        
        [Utility Class]:
        {{ClaimProcessingUtil}}
        
        --- CONTEXT END ---
        """)
    String generateCode(@UserMessage String userMessage,
                        @V("claimDto") String dtoCode,
                        @V("ClaimProcessingUtil") String utilCode);
}