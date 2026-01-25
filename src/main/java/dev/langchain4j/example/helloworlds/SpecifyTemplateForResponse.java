package dev.langchain4j.example.helloworlds;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;
import static java.time.Duration.ofSeconds;
import static java.util.Arrays.asList;

public class SpecifyTemplateForResponse {

    static class Simple_Prompt_Template_Example {

        public static void main(String[] args) {

            ChatModel model = OpenAiChatModel.builder()
                    .apiKey(ApiKeys.OPENAI_API_KEY)
                    .modelName(GPT_4_O_MINI)
                    .timeout(ofSeconds(60))
                    .build();

            var myCustomRunningModel = OllamaChatModel.builder()
                    .baseUrl("http://localhost:11434")
                    .modelName("java-brain") // <--- MUST MATCH the name you used in 'ollama create'
                    .temperature(0.7)        // 0.0 = Precise answers (good for coding/logic)
                    .build();

            String template = "Create a recipe for a {{dishType}} with the following ingredients: {{ingredients}}";
            PromptTemplate promptTemplate = PromptTemplate.from(template);

            Map<String, Object> variables = new HashMap<>();
            variables.put("dishType", "oven dish");
            variables.put("ingredients", "potato, tomato, feta, olive oil");

            Prompt prompt = promptTemplate.apply(variables);

            String response = myCustomRunningModel.chat(prompt.text());

            System.out.println(response);
        }

    }

    static class Structured_Prompt_Template_Example {
        @StructuredPrompt({
                "Create a {{dish}} recipe using {{ingredients}}. for a bachelor",
                "Structure your answer in the following way:",
                "Required ingredients in exact quantity and spoon:",
                "- ...",
                "- ...",
                "Instructions divided in 4 friends as work (Saahil, Piyush, Sid, Shivam):",
                "- ...",
                "- ..."
        })
        static class CreateRecipePrompt {

            String dish;
            List<String> ingredients;

            CreateRecipePrompt(String dish, List<String> ingredients) {
                this.dish = dish;
                this.ingredients = ingredients;
            }
        }

        public static void main(String[] args) {

            ChatModel model = OpenAiChatModel.builder()
                    .apiKey(ApiKeys.OPENAI_API_KEY)
                    .modelName(GPT_4_O_MINI)
                    .timeout(ofSeconds(60))
                    .build();

            Structured_Prompt_Template_Example.CreateRecipePrompt createRecipePrompt = new Structured_Prompt_Template_Example.CreateRecipePrompt(
                    "non veg dish",
                    asList("cucumber", "tomato", "paneer", "onion", "capsicum")
            );

            Prompt prompt = StructuredPromptProcessor.toPrompt(createRecipePrompt);

            String recipe = model.chat(prompt.text());

            System.out.println(recipe);
        }
    }
}
