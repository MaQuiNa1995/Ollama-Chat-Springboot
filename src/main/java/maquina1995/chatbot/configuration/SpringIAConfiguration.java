package maquina1995.chatbot.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import maquina1995.chatbot.tool.ToolConfiguration;

@SpringBootConfiguration
public class BeanConfiguration {
	/**
     * Crea un bean de ChatClient inyectando el ChatModel que ya fue creado
     * automaticamente por el spring-ai-ollama-spring-boot-starter.
     * @param chatModel El modelo de chat (OllamaChatModel) inyectado automaticamente.
     * @return Una instancia del ChatClient.
     */
    @Bean
    public ChatClient customChatClient(ChatModel chatModel, ToolConfiguration toolConfiguration) {
        return ChatClient.builder(chatModel)
                .build();
    }
}
