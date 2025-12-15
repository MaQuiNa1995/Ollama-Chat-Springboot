package maquina1995.chatbot.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class BeanConfiguration {
	/**
     * Crea un bean de ChatClient inyectando el ChatModel que ya fue creado
     * automaticamente por el spring-ai-ollama-spring-boot-starter.
     * @param chatModel El modelo de chat (OllamaChatModel) inyectado automaticamente.
     * @return Una instancia del ChatClient.
     */
    @Bean
    public ChatClient customChatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                // Opcional: Aqui puedes configurar opciones por defecto
                // .defaultSystem("Eres un asistente de programaci�n experto en Java.")
                // .defaultToolNames("myToolFunction")
                .build();
    }
}
