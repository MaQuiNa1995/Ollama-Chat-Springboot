package maquina1995.ollama.chat.springboot.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class BeanConfiguration {
	/**
     * Crea un bean de ChatClient inyectando el ChatModel que ya fue creado
     * automáticamente por el spring-ai-ollama-spring-boot-starter.
     * @param chatModel El modelo de chat (OllamaChatModel) inyectado automáticamente.
     * @return Una instancia del ChatClient.
     */
    @Bean
    public ChatClient customChatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                // Opcional: Aquí puedes configurar opciones por defecto
                // .defaultSystem("Eres un asistente de programación experto en Java.")
                // .defaultToolNames("myToolFunction")
                .build();
    }
}
