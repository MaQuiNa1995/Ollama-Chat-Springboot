package maquina1995.ollama.chat.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

/**
 * Main con llamada desde consola a contenedor de docker con mistral como modelo de lenguaje
 * 
 * @author MaQuiNa1995
 *
 */
@Log4j2
@SpringBootApplication
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

	/**
	 * Objeto propio de Spring boot encargado de la comunicacion con el modelo
	 */
	private final ChatClient chatClient;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

			System.out.print("Formula tu pregunta: ");
			String prompt = reader.readLine();
			
		    Flux<String> streamDeContenido = chatClient.prompt(prompt) // pasamos el prompt
                    .stream() // marcamos que queremos que la respuesta sea en streaming
                    .content(); // Obtenemos la referencia al flux para poder subscribirnos y recibir la respuesta de poco a poco es decir fragmento a fragmento

		    streamDeContenido.subscribe(
		    		// Fragmento recibido 
		    		System.out::print,
		    		// Fragmento no recibido (error)
		            error ->  System.out.println("\n[ERROR de Streaming]: " + error.getMessage()),
		            // Se acabÃ³ de procesar todo el contenido
		            () ->  System.out.println("\n\n--- Generación Completada ---") 
		        );
		    
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}


}